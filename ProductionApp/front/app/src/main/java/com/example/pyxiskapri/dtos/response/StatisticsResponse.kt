package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class StatisticsResponse (
    @SerializedName("FullCoverImagePath")
    var coverImage: String,
    @SerializedName("AverageGrade")
    var averageGrade: Double,
    @SerializedName("Count")
    var gradesCount: Int
)