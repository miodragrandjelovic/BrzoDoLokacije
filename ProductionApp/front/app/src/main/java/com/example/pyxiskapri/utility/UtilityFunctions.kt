package com.example.pyxiskapri.utility

import android.content.Context
import android.net.Uri
import android.se.omapi.Session
import android.text.TextUtils
import android.util.DisplayMetrics
import androidx.documentfile.provider.DocumentFile
import com.example.pyxiskapri.models.ProgressRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


object UtilityFunctions {

    fun checkUsernameLogged(username: String, context: Context): Boolean{
        var userData = SessionManager(context).fetchUserData()
        if(userData != null && userData.username == username)
            return true
        return false
    }

    fun getFullImagePath(imagePath: String): String{
        return Constants.BASE_URL + "/" + imagePath
    }

    // Za pretvaranje svih polja u string za multipart
    fun requestBodyFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    // Dobijanje trenutnog fajla iz Uri-ja koji se moze poslati kroz Multipart zahtev
    fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String, suffix: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, suffix, context.cacheDir)
            org.apache.commons.io.FileUtils.copyInputStreamToFile(stream,file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Uzimanje ekstenzije Uri-ja
    fun getUriExtention(context: Context, uri: Uri): String{
        var fileName: String = DocumentFile.fromSingleUri(context, uri)?.name!!
        var extention: String = fileName.split(".").last()
        return ".$extention"
    }

    // Pretvara Uri slike u gotov MultipartBody.Part objekat koji je argument api zahteva
    fun uriToMultipartPart(context: Context, imageUri: Uri, partName: String, fileNamePrefix: String): MultipartBody.Part{
        val imageFile: File = createTmpFileFromUri(context, imageUri, fileNamePrefix, getUriExtention(context, imageUri))!!
        imageFile.deleteOnExit()
        val imageAsRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, imageFile.name, imageAsRequestBody)
    }

    // Pretvara Uri slike u gotov MultipartBody.Part sa pratnjom progresa
    fun uriToProgressMultipartPart(context: Context, imageId: Int, imageUri: Uri, partName: String, fileNamePrefix: String, listener: ProgressRequestBody.UploadListener): MultipartBody.Part{
        val imageFile: File = createTmpFileFromUri(context, imageUri, fileNamePrefix, getUriExtention(context, imageUri))!!
        imageFile.deleteOnExit()
        val imageAsRequestBody = ProgressRequestBody(imageId, imageFile, "image/*", listener)
        return MultipartBody.Part.createFormData(partName, imageFile.name, imageAsRequestBody)
    }


    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }



}