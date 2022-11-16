package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("CommenterImage")
    var commenterImage: String,
    @SerializedName("CommenterUsername")
    var commenterUsername: String,
    @SerializedName("CommentText")
    var commentText: String,
    @SerializedName("CreationDate")
    var creationDate: String,
    @SerializedName("LikeStatus")
    var likeStatus: Int,
    @SerializedName("LikeCount")
    var likeCount: Int,
    @SerializedName("DislikeCount")
    var dislikeCount: Int,
    @SerializedName("ReplyCount")
    var replyCount: Int
)