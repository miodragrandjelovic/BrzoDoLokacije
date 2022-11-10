package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class PostAdditionalData(
    @SerializedName("description")
    var postDescription: String,
    @SerializedName("images")
    var additionalImages: ArrayList<String>,
)
