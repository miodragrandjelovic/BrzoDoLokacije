package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.PostListAdapter
import com.example.pyxiskapri.dtos.response.PostResponse
import kotlinx.android.synthetic.main.activity_home.*
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

        fillPostsRV()

        setupButtonNewPost()
        setupButtonLogout()
    }

    override fun onRestart() {
        super.onRestart();
        fillPostsRV()
    }

    private fun setupButtonLogout(){
//        btn_notifications.setOnClickListener {
//            sessionManager.clearToken()
//            val intent = Intent (this, MainActivity::class.java);
//            startActivity(intent);
//        }
    }

    private fun setupButtonNewPost(){
        btn_newPost .setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setPostsRV(){
        postListAdapter = PostListAdapter(mutableListOf())
        rv_posts.adapter = postListAdapter
        rv_posts.layoutManager = LinearLayoutManager(this)
    }

    private fun fillPostsRV(){

        apiClient.getPostService(this).getUserPosts(sessionManager.fetchUserData()!!.username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse( call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful)
                        postListAdapter.setPostList(sessionManager.fetchUserData()!!.username, response.body()!!)

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}