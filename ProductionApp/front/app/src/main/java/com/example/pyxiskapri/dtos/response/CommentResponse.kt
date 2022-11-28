package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("profileImagePath")
    var commenterImage: String,
    @SerializedName("username")
    var commenterUsername: String,
    @SerializedName("commentText")
    var commentText: String,
    @SerializedName("dateOfCommenting")
    var creationDate: String,
    @SerializedName("likeStatus")
    var likeStatus: Int = 0,
    @SerializedName("likeCount")
    var likeCount: Int = 0,
    @SerializedName("dislikeCount")
    var dislikeCount: Int = 0,
    @SerializedName("replyCount")
    var replyCount: Int = 0


)