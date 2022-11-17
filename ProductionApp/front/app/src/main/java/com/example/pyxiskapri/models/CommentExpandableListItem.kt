package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.CommentResponse

data class CommentExpandableListItem(
    var id: Int,
    var commenterImage: String,
    var commenterUsername: String,
    var commentText: String,
    var creationDate: String,
    var likeStatus: Int,
    var likeCount: Int,
    var dislikeCount: Int,
    var replyCount: Int,
    var replyList: ArrayList<CommentResponse>
){
    constructor(comment: CommentResponse) : this(
        id = comment.id,
        commenterImage = comment.commenterImage,
        commenterUsername = comment.commenterUsername,
        commentText = comment.commentText,
        creationDate = comment.creationDate,
        likeStatus = comment.likeStatus,
        likeCount = comment.likeCount,
        dislikeCount = comment.dislikeCount,
        replyCount = comment.replyCount,
        replyList = arrayListOf()
    )

}
