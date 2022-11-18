package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class AddFollowRequest(
    @SerializedName("username")
    var username:String
)
