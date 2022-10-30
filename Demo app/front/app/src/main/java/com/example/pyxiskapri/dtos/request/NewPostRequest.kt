package com.example.pyxiskapri.dtos.request

import android.net.Uri
import com.example.pyxiskapri.models.UserData
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class NewPostRequest (
    @SerializedName("location")
    var location: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("images")
    var images: ArrayList<Uri?>,
    @SerializedName("dateAndTime")
    var dateAndTime: String,
    @SerializedName("user")
    var user: UserData


)


