package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.PostResponse

data class PostListItem(
    var username: String,
    var image: String,
    var likeCount: Int,
    var viewCount: Int
)
{
    constructor(response: PostResponse) : this("", response.image, response.likeCount, response.viewCount)
}
