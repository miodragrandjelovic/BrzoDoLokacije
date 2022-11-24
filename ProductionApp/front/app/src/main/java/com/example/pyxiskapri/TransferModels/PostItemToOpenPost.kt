package com.example.pyxiskapri.TransferModels

import android.graphics.Bitmap
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import java.io.Serializable

data class PostItemToOpenPost(
    var id: Int = 0,
    var isLiked: Boolean = false,
    var ownerUsername: String = "",
    var ownerImage: Bitmap,
    var coverImage: Bitmap,
    var likeCount: Int = 0,
    var viewCount: Int = 0
) : Serializable
{
    constructor(response: PostListItem, ownerImage: Bitmap, coverImage: Bitmap) : this(
        response.id,
        response.isLiked,
        response.ownerUsername,
        ownerImage,
        coverImage,
        response.likeCount,
        response.viewCount
    )
}