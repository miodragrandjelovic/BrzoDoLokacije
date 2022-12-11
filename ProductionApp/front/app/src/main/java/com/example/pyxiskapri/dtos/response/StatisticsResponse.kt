package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class StatisticsResponse (
    @SerializedName("fullCoverImagePath")
    var coverImage: String,
    @SerializedName("averageGrade")
    var averageGrade: Double,
    @SerializedName("count")
    var gradesCount: Int
)