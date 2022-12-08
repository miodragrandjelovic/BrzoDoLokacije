package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class GradeResponse(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("postId")
    var postId: Int,
    @SerializedName("grade")
    var grade: Int,
    @SerializedName("average")
    var averageGrade: Double,
    @SerializedName("count")
    var gradesCount: Int
)
