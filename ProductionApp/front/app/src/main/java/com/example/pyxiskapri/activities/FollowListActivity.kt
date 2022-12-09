package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.FollowUserAdapter
import com.example.pyxiskapri.adapters.UserPostsAdapter
import com.example.pyxiskapri.dtos.response.FollowUserResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_follow_list.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowListActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    lateinit var followUserAdapter: FollowUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_list)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)


        var followList = ActivityTransferStorage.followList

        setupFollowUserAdapter()
        showFollows(followList)


        setupHome()
        setupAddPost()
        setupButtonMessages()
        setupDiscover()
    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_flwDrawerNav.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupFollowUserAdapter() {
        followUserAdapter = FollowUserAdapter(mutableListOf())
        rv_follows.layoutManager = LinearLayoutManager(this)
        rv_follows.adapter = followUserAdapter
        (rv_follows.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun showFollows(followList: FollowList) {

        //followers false
        if(!followList.type)
        {
            apiClient.getUserService(this).getFollowers(followList.username).enqueue(object : Callback<ArrayList<FollowUserResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<FollowUserResponse>>,
                    response: Response<ArrayList<FollowUserResponse>>
                )
                {
                    followUserAdapter.setFollowUsersList(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<FollowUserResponse>>, t: Throwable) {

                }

            })
        }

        else
        {
            apiClient.getUserService(this).getFollowing(followList.username).enqueue(object : Callback<ArrayList<FollowUserResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<FollowUserResponse>>,
                    response: Response<ArrayList<FollowUserResponse>>
                )
                {
                    followUserAdapter.setFollowUsersList(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<FollowUserResponse>>, t: Throwable) {

                }

            })
        }

    }


    private fun setupHome() {
        flw_btn_home.setOnClickListener(){
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAddPost() {
        flw_btn_newPost.setOnClickListener(){
            val intent = Intent (this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupButtonMessages() {
        flw_btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupDiscover(){
        flw_btn_discover.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            startActivity(intent);
        }
    }

}