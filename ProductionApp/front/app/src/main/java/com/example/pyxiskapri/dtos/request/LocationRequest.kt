package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Name")
    var locationName: String
)
