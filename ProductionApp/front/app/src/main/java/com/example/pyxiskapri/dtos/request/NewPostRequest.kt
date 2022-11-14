package com.example.pyxiskapri.dtos.request

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.pyxiskapri.models.UserData
import com.google.gson.annotations.SerializedName

data class NewPostRequest (
    @SerializedName("Description")
    var description: String = "",
    @SerializedName("Address")
    var address: String = "",
    @SerializedName("City")
    var city: String = "",
    @SerializedName("Country")
    var country: String = "",
    @SerializedName("Longitude")
    var longitude: Double = 0.00,
    @SerializedName("Latitude")
    var latitude: Double = 0.00,
    @SerializedName("CoverImage")
    var coverImage: String = "",
    @SerializedName("Images")
    var images: ArrayList<String> = arrayListOf()
)
