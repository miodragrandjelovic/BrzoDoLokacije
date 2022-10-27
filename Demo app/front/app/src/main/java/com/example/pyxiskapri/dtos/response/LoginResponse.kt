package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    var token: String
)