package com.example.pyxiskapri.dtos.request

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.pyxiskapri.models.UserData
import com.google.gson.annotations.SerializedName

data class NewPostRequest (
    @SerializedName("description")
    var description: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("locationName")
    var locationName: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("country")
    var country: String = "",
    @SerializedName("longitude")
    var longitude: Double = 0.00,
    @SerializedName("latitude")
    var latitude: Double = 0.00,
    @SerializedName("coverImage")
    var coverImage: String = "",
    @SerializedName("images")
    var images: ArrayList<String> = arrayListOf()
)
