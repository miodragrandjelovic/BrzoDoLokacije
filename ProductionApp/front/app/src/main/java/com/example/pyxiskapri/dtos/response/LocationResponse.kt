package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("distance")
    var distance: Double,
    @SerializedName("posts")
    var posts: ArrayList<PostResponse>
)
