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

        postListAdapter.addPost()
        postListAdapter.addPost()
        postListAdapter.addPost()
    }

    private fun setPostsRV(){
        postListAdapter = PostListAdapter(mutableListOf())
        rv_posts.adapter = postListAdapter
        rv_posts.layoutManager = LinearLayoutManager(this)
    }

}