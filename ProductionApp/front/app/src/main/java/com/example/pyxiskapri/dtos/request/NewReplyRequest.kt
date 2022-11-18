package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class NewReplyRequest(
    @SerializedName("CommentID")
    var commentId: Int,
    @SerializedName("Comment")
    var commentText: String
)
