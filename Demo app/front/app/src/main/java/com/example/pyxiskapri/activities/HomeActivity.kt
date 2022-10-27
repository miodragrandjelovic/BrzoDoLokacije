package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.PostListAdapter
import com.example.pyxiskapri.dtos.response.MessageResponse
import kotlinx.android.synthetic.main.activity_home.*
import com.example.pyxiskapri.models.UserData
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    lateinit var postListAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setPostsRV()
        setUserData()
        setLogoutTEMPButton()
        setTestAddButton()

    }

    private fun setPostsRV(){
        postListAdapter = PostListAdapter(mutableListOf())
        rv_posts.adapter = postListAdapter
        rv_posts.layoutManager = LinearLayoutManager(this)
    }

    private fun setTestAddButton(){
        btn_testAdd.setOnClickListener {
            postListAdapter.addPost()
        }
    }


    private fun setUserData(){
        val userData: UserData = sessionManager.fetchUserData() ?: return
        tv_ownerUsername.text = userData.username
    }

    private fun setLogoutTEMPButton(){
        btn_logoutTEMP.setOnClickListener{
            sessionManager.clearToken()
            val intent = Intent (this, MainActivity::class.java);
            startActivity(intent);
        }
    }

}