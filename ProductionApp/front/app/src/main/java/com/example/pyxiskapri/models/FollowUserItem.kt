package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.FollowUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import java.io.Serializable

data class FollowUserItem(

    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var profileImage: String = "",
    var isFollowed: Boolean = false

)
: Serializable {
    constructor(response: FollowUserResponse) : this(
        response.firstName,
        response.lastName,
        response.username,
        response.profileImage,
        response.isFollowed
    )
}

