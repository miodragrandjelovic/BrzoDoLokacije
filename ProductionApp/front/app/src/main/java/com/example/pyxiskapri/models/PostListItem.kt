package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.PostResponse
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class PostListItem(
    var id: Int = 0,
    var isLiked: Boolean = false,
    var ownerUsername: String = "",
    var ownerImage: String = "",
    var coverImage: String = "",
    var likeCount: Int = 0,
    var viewCount: Int = 0
) : Serializable
{
    constructor(response: PostResponse) : this(response.id, response.isLiked, response.ownerUsername, response.ownerImage, response.coverImage, response.likeCount, response.viewCount)
}
