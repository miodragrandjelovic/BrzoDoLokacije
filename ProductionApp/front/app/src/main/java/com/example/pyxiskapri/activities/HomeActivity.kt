package com.example.pyxiskapri.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.PostListAdapter
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_home.*
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

        setupNavButtons()
    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNav.getFragment<DrawerNav>().showDrawer()
    }


    override fun onRestart() {
        super.onRestart()
        fillPostsRV()
    }

    private fun setupNavButtons(){
        setupButtonNewPost()

    }

    private fun setupButtonNewPost(){
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setPostsRV(){
        postListAdapter = PostListAdapter(mutableListOf())
        rv_posts.adapter = postListAdapter
        rv_posts.layoutManager = LinearLayoutManager(this)
        (rv_posts.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun fillPostsRV(){

        apiClient.getPostService(this).getAllPosts()
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse( call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {
                        postListAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })
    }

}