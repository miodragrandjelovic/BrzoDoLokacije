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
    var friendsOnly: Boolean
)
