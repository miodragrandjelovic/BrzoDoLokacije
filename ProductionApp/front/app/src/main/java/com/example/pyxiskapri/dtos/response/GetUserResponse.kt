package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("profileImagePath")
    var profileImage: String,
    @SerializedName("userFollowersCount")
    var followersCount: Int,
    @SerializedName("userFollowingCount")
    var followingCount: Int

)
