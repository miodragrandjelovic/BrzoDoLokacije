package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CommentResponse(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("CommentText")
    var commentText: String,
    @SerializedName("CreationDate")
    var creationDate: Date,
    @SerializedName("LikeCount")
    var likeCount: Int,
    @SerializedName("DislikeCount")
    var dislikeCount: Int,
    @SerializedName("ReplyCount")
    var replyCount: Int,
)