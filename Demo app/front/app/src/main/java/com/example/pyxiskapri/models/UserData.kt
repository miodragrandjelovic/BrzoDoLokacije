package com.example.pyxiskapri.models

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("Email")
    var email: String,
    @SerializedName("Username")
    var username: String,
    @SerializedName("exp")
    var exp: Int
)
