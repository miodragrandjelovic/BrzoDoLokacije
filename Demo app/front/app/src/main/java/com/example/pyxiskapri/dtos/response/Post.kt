package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Post(
    @SerializedName("OwnerUsername")
    var ownerUsername: String,
    @SerializedName("CreationDate")
    var creationDate: Date,
    @SerializedName("Description")
    var description: String,
    @SerializedName("Image")
    var image: String,
    @SerializedName("Liked")
    var liked: Boolean,
    @SerializedName("Disliked")
    var disliked: Boolean,
    @SerializedName("Followed")
    var followed: Boolean,
    @SerializedName("Reported")
    var reported: Boolean
)
