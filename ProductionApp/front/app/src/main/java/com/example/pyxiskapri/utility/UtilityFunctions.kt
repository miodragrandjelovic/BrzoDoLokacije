package com.example.pyxiskapri.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object UtilityFunctions {

    fun base64ToBitmap(base64Image: String) : Bitmap {
        val imageData = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
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
        return "." + (DocumentFile.fromSingleUri(context, uri)?.name ?: "").split('.')[1]
    }
    // Pretvara Uri slike u gotov MultipartBody.Part objekat koji je argument api zahteva
    fun uriToMultipartPart(context: Context, imageUri: Uri, partName: String, fileNamePrefix: String): MultipartBody.Part{
        val imageFile: File = createTmpFileFromUri(context, imageUri, fileNamePrefix, getUriExtention(context, imageUri))!!
        imageFile.deleteOnExit()
        val imageAsRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, imageFile.name, imageAsRequestBody)
    }

}