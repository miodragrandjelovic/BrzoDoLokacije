package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
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
    @SerializedName("grade")
    var usersGrade: Int,
    @SerializedName("averageGrade")
    var averageGrade: Double,
    @SerializedName("count")
    var gradesCount: Int
)
