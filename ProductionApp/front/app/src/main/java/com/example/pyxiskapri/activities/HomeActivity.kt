package com.example.pyxiskapri.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.FollowedPostListAdapter
import com.example.pyxiskapri.adapters.PostListAdapter
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Resources
import kotlinx.android.synthetic.main.activity_chat_main.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_messages
import kotlinx.android.synthetic.main.activity_home.btn_newPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var postListAdapter: PostListAdapter

    private lateinit var followedPostListAdapter: FollowedPostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setSwipeRefresh()

        setPostsRV()
        setFollowedPostsRV()

        fillPostsRV()
        fillFollowedPostsRv()

        setupNavButtons()
    }

    private fun setSwipeRefresh(){

        abl_home.addOnOffsetChangedListener { _, verticalOffset ->
            srl_home.isEnabled = verticalOffset == 0
        }

        srl_home.setProgressBackgroundColorSchemeResource(R.color.red)
        srl_home.setColorSchemeResources(R.color.white)

        srl_home.setOnRefreshListener {
            setPostsRV()
            setFollowedPostsRV()
            fillPostsRV()
            fillFollowedPostsRv()

            srl_home.isRefreshing = false
        }
    }


    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNav.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupNavButtons(){
        setupButtonNewPost()
        setupButtonMessages()
    }

    private fun setupButtonNewPost(){
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupButtonMessages(){
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }
    }




    private fun setPostsRV(){

        postListAdapter = PostListAdapter(mutableListOf())
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = postListAdapter
        (rv_posts.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

    private fun setFollowedPostsRV() {
        followedPostListAdapter = FollowedPostListAdapter(mutableListOf())
        rv_f_posts.adapter = followedPostListAdapter
        rv_f_posts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        (rv_f_posts.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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

    private fun fillFollowedPostsRv(){

        apiClient.getPostService(this).getFollowingPosts()
            .enqueue(object : Callback<ArrayList<PostResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<PostResponse>>,
                    response: Response<ArrayList<PostResponse>>
                )
                {

                    if(response.isSuccessful){

                        if(response.body()!!.size==0)
                        {
                            ll_follow.isGone=true
                        }
                        else
                        {
                            ll_follow.isGone=false
                            followedPostListAdapter.setFollowedPostList(response.body()!!)
                        }


                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })

    }


}
