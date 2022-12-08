package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class GradeRequest(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("postId")
    var postId: Int,
    @SerializedName("grade")
    var grade: Int
)
