package com.example.pyxiskapri.dtos.request

import com.google.gson.annotations.SerializedName

data class LikeDislikeRequest(
    @SerializedName("CommendId")
    var commentId: Int
)
