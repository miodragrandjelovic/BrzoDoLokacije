package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class NewCommentRequest(
    @SerializedName("PostId")
    var postId: Int,
    @SerializedName("CommentParentId")
    var parentId: Int,
    @SerializedName("Comment")
    var commentText: String

)
