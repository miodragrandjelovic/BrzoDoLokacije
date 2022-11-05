package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class EditUserRequest(

    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String



)
