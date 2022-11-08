package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostFullResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("ownerUsername")
    var ownerUsername: String,
    @SerializedName("ownerImage")
    var ownerImage: String,
    @SerializedName("postDescription")
    var postDescription: String,
    @SerializedName("coverImage")
    var coverImage: String,
    @SerializedName("additionalImages")
    var additionalImages: ArrayList<String>,
    @SerializedName("numberOfLikes")
    var likeCount: Int,
    @SerializedName("numberOfViews")
    var viewCount: Int
)
