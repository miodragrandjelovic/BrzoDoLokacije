package com.example.pyxiskapri.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
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
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_messages
import kotlinx.android.synthetic.main.activity_home.btn_newPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private var selectedSortType: Int = 0

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

        setupSortButtons()

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
            finish()
        }
    }

    private fun setupButtonMessages(){
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
            finish()
        }
    }


    private fun clearButtonStates(){
        btn_sortPopular.backgroundTintList = this.resources.getColorStateList(R.color.dark_gray)
        btn_sortNewest.backgroundTintList = this.resources.getColorStateList(R.color.dark_gray)
        btn_sortDiscussed.backgroundTintList = this.resources.getColorStateList(R.color.dark_gray)
        btn_sortNoticed.backgroundTintList = this.resources.getColorStateList(R.color.dark_gray)
    }

    private fun setupSortButtons(){
        btn_sortPopular.setOnClickListener{
            clearButtonStates()
            selectedSortType = 0
            setPostsRV()
            fillPostsRV()
            btn_sortPopular.backgroundTintList = this.resources.getColorStateList(R.color.red)
        }

        btn_sortNewest.setOnClickListener{
            clearButtonStates()
            selectedSortType = 1
            setPostsRV()
            fillPostsRV()
            btn_sortNewest.backgroundTintList = this.resources.getColorStateList(R.color.red)
        }

        btn_sortDiscussed.setOnClickListener{
            clearButtonStates()
            selectedSortType = 2
            setPostsRV()
            fillPostsRV()
            btn_sortDiscussed.backgroundTintList = this.resources.getColorStateList(R.color.red)
        }

        btn_sortNoticed.setOnClickListener{
            clearButtonStates()
            selectedSortType = 3
            setPostsRV()
            fillPostsRV()
            btn_sortNoticed.backgroundTintList = this.resources.getColorStateList(R.color.red)
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

        apiClient.getPostService(this).getAllPosts(selectedSortType)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse( call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {
                        postListAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
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
                            rv_f_posts.isGone=true
                        }
                        else
                        {
                            rv_f_posts.isGone=false
                            followedPostListAdapter.setFollowedPostList(response.body()!!)
                        }


                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })

    }


}
