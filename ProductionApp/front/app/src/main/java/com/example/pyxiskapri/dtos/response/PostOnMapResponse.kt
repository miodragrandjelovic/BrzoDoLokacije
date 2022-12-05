package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostOnMapResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var ownerUsername: String,
    @SerializedName("fullProfileImagePath")
    var ownerImage: String,
    @SerializedName("fullCoverImagePath")
    var coverImage: String,
    @SerializedName("dateCreated")
    var postDate: String,
    @SerializedName("numberOfLikes")
    var likeCount: Int,
    @SerializedName("numberOfViews")
    var viewCount: Int,
    @SerializedName("isLiked")
    var isLiked: Boolean,
    @SerializedName("location")
    var location: Int,
    @SerializedName("city")
    var city: Int,
    @SerializedName("country")
    var country: Boolean
)
