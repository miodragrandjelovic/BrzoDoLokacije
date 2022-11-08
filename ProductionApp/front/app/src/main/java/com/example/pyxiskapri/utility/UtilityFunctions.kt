package com.example.pyxiskapri.utility

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_open_post.*

object UtilityFunctions {

    public fun base64ToBitmap(base64Image: String) : Bitmap {
        val imageData = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }

}