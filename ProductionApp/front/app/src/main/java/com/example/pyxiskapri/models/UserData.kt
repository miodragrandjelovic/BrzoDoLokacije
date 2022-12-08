package com.example.pyxiskapri.models

import com.example.pyxiskapri.utility.SessionManager
import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName(SessionManager.USERNAME_KEY)
    var username: String,

    @SerializedName(SessionManager.FIRSTNAME_KEY)
    var firstName: String,

    @SerializedName(SessionManager.LASTNAME_KEY)
    var lastName: String,

    @SerializedName(SessionManager.PROFILE_IMAGE_KEY)
    var profileImagePath: String,

    @SerializedName(SessionManager.EMAIL_KEY)
    var email: String,

    @SerializedName(SessionManager.ROLE_KEY)
    var role: String,

    @SerializedName(SessionManager.EXP_KEY)
    var exp: Int
)
