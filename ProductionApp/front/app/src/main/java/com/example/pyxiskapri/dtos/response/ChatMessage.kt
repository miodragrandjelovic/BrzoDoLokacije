package com.example.pyxiskapri.dtos.response

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("id")
    var id: Long,
    @SerializedName("usernameSender")
    var sender: String,
    @SerializedName("usernameReceiver")
    var receiver: String,
    @SerializedName("text")
    var text: String,
    @SerializedName("time")
    var time: String
)
