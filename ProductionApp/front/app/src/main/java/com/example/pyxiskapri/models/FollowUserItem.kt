package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.FollowUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import java.io.Serializable

data class FollowUserItem(

    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var profileImage: String = ""

)
: Serializable {
    constructor(response: FollowUserResponse) : this(
        response.id,
        response.firstName,
        response.lastName,
        response.username,
        response.profileImage
    )
}

