package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class MapSearchRequest(
    @SerializedName("search")
    var search: String,
    @SerializedName("sortType")
    var sortType: Int,
    @SerializedName("countOfResults")
    var countOfResults: Int,
    @SerializedName("friendsOnly")
    var friendsOnly: Boolean,

    @SerializedName("name")
    var name: String = "",
    @SerializedName("searchType")
    var searchType: Int = 0,
    @SerializedName("longitude")
    var longitude: Double = 0.0,
    @SerializedName("latitude")
    var latitude: Double = 0.0,
    @SerializedName("distance")
    var distance: Double = 0.0
)
