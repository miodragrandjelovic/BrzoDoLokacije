package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Name")
    var locationName: String
)
