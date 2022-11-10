package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.PostResponse
import java.io.Serializable

data class PostListItem(
    var id: Int,
    var ownerUsername: String,
    var ownerImage: String,
    var coverImage: String,
    var likeCount: Int,
    var viewCount: Int
) : Serializable
{
    constructor(response: PostResponse) : this(response.id, response.ownerUsername, response.ownerImage, response.coverImage, response.likeCount, response.viewCount)
}
