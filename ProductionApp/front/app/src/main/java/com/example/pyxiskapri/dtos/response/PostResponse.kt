package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("coverImage")
    var image: String,
    @SerializedName("numberOfLikes")
    var likeCount: Int,
    @SerializedName("numberOfViews")
    var viewCount: Int
)
