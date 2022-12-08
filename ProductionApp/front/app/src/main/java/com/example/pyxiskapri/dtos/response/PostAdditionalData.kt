package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostAdditionalData(
    @SerializedName("description")
    var postDescription: String,
    @SerializedName("images")
    var additionalImages: ArrayList<String>,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("address")
    var address: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("numberOfComments")
    var commentCount: Int
)
