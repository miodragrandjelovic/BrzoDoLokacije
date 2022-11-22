package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class FriendResponse(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Username")
    var friendUsername: String,
    @SerializedName("Image")
    var friendImage: String,
    @SerializedName("LastMessage")
    var lastMessage: String,
    @SerializedName("LastMessageTime")
    var lastMessageTime: String
)
