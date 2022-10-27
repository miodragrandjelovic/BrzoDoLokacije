package com.example.pyxiskapri.dtos.request.response

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("usernameOrEmail")
    var usernameOrEmail: String,
    @SerializedName("password")
    var password: String
)