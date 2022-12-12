package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class SearchRequest(

    @SerializedName("username")
    var username: String,
    @SerializedName("search")
    var search: String

)
