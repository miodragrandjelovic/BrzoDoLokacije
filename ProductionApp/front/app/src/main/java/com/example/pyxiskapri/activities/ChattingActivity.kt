package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.activity_chatting.btn_discover
import kotlinx.android.synthetic.main.activity_chatting.btn_messages
import kotlinx.android.synthetic.main.activity_chatting.btn_newPost

class ChattingActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    private lateinit var friendData: FriendResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        apiClient = ApiClient()

        collectFriendData()

        setupNavButtons()
    }

    private fun collectFriendData(){
        friendData = ActivityTransferStorage.chatToChatting
    }

    private fun setupNavButtons(){
        // NEW POST
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }

        // MAPS
        btn_discover.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            startActivity(intent);
        }

        // HOME
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }

        // MESSAGES
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }

        // NOTIFICATIONS

    }






}