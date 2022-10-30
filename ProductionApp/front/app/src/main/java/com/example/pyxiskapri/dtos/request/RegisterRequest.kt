package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("FirstName")
    var firstName: String,
    @SerializedName("LastName")
    var lastName: String,
    @SerializedName("Email")
    var email: String,
    @SerializedName("Username")
    var username: String,
    @SerializedName("Password")
    var password: String
)