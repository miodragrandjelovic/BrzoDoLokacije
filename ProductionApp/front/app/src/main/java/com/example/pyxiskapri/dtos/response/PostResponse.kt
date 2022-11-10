package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("isLiked")
    var isLiked: Boolean,
    @SerializedName("username")
    var ownerUsername: String,
    @SerializedName("profileImage")
    var ownerImage: String,
    @SerializedName("coverImage")
    var coverImage: String,
    @SerializedName("numberOfLikes")
    var likeCount: Int,
    @SerializedName("numberOfViews")
    var viewCount: Int
)
