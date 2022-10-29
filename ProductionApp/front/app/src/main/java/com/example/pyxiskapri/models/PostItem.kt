package com.example.pyxiskapri.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Post(
    @SerializedName("OwnerUsername")
    var ownerUsername: String,
    @SerializedName("Image")
    var image: String,

    @SerializedName("LikeCount")
    var likeCount: Int,
    @SerializedName("ViewCount")
    var viewCount: Int,

    @SerializedName("Liked")
    var liked: Boolean,
    @SerializedName("Disliked")
    var disliked: Boolean,
    @SerializedName("Followed")
    var followed: Boolean,
    @SerializedName("Reported")
    var reported: Boolean
)
