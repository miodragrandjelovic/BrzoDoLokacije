package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class FollowUserResponse(

    @SerializedName("firstName")
    var firstName: String ,
    @SerializedName("lastName")
    var lastName: String ,
    @SerializedName("username")
    var username: String ,
    @SerializedName("profileImage")
    var profileImage: String,
    @SerializedName("isFollowed")
    var isFollowed: Boolean

)
