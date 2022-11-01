package com.example.pyxiskapri.dtos.request

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.pyxiskapri.models.UserData
import com.google.gson.annotations.SerializedName

data class NewPostRequest (
    @SerializedName("coverImage")
    var coverImage: String,
    @SerializedName("images")
    var images: ArrayList<String>,
    @SerializedName("locationId")
    var location: Int,
    @SerializedName("description")
    var description: String
)
