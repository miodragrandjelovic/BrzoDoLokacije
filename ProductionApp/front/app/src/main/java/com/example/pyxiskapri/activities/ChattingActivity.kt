package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    private lateinit var friendData: FriendResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        apiClient = ApiClient()


        setupGoBackButton()

        setupFriendData()
    }

    private fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_chattingDrawer.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupGoBackButton(){
        btn_goBack.setOnClickListener {
            finish()
        }
    }


    private fun setupFriendData(){
        friendData = ActivityTransferStorage.chatToChatting
    }


}