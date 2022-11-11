package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class ForeignUserRequest(
    @SerializedName("username")
    var username: String
)
