package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String

)
