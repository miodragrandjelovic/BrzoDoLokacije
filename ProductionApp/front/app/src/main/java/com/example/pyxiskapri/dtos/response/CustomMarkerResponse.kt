package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class CustomMarkerResponse(
    @SerializedName("id")
    var postId: Int,
    @SerializedName("coverImagePath")
    var coverImage: String,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("numberOfLikes")
    var numberOfLikes: Int

)
