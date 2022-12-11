package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import kotlinx.android.synthetic.main.activity_chat_main.*
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.activity_chatting.navMenuView

class ChattingActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    private lateinit var friendData: FriendResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        apiClient = ApiClient()

        setupChattingRV()

        collectAndSetFriendData()

        navMenuView.setIndicator(Constants.NavIndicators.MESSAGES)
    }


    private fun setupChattingRV(){

    }

    private fun collectAndSetFriendData(){
        friendData = ActivityTransferStorage.chatToChatting
    }







}