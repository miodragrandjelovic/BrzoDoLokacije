package com.example.pyxiskapri.dtos.request

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.pyxiskapri.models.UserData
import com.google.gson.annotations.SerializedName

data class NewPostRequest (
    @SerializedName("location")
    var location: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("coverImage")
    var coverImage: ByteArray,
    @SerializedName("images")
    var images: ArrayList<ByteArray>,
    @SerializedName("user")
    var user: UserData


)
