package com.example.pyxiskapri.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pyxiskapri.R
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.activity_chat_main.*

class ChatMainActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)

        apiClient = ApiClient()

    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_chatMainDrawerNav.getFragment<DrawerNav>().showDrawer()
    }



}