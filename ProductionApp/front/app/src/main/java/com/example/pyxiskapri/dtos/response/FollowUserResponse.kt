package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class FollowUserResponse(

    @SerializedName("id")
    var id: Int,
    @SerializedName("firstName")
    var firstName: String ,
    @SerializedName("lastName")
    var lastName: String ,
    @SerializedName("username")
    var username: String ,
    @SerializedName("profileImage")
    var profileImage: String

)
