package com.example.pyxiskapri.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import java.io.File

object UtilityFunctions {

    public fun base64ToBitmap(base64Image: String) : Bitmap {
        val imageData = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }

    public fun getFullImagePath(imagePath: String): String{
        return Constants.BASE_URL + "/" + imagePath
    }

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

    fun getUriExtention(context: Context, uri: Uri): String{
        return "." + (DocumentFile.fromSingleUri(context, uri)?.name ?: "").split('.')[1]
    }

}