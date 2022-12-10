package com.example.pyxiskapri.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isGone
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.gvForeignPostAdapter
import com.example.pyxiskapri.dtos.request.AddFollowRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.*
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.ib_follow
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.ib_following
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.tv_follow_ing
import kotlinx.android.synthetic.main.modal_confirm_follow.*
import kotlinx.android.synthetic.main.modal_confirm_unfollow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForeignProfileGridActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    lateinit var username:String

    lateinit var gvForeignPostAdapter: gvForeignPostAdapter

    override fun onRestart() {
        super.onRestart()
        getForeignUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile_grid)


        ll_map_f.setOnClickListener(){
            val intent = Intent (this, ForeignProfileMapActivity::class.java);
            intent.putExtra("username", username)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent);
        }

        val bundle = intent.extras
        username = bundle?.getString("username")!!

        apiClient= ApiClient()
        sessionManager= SessionManager(this)

        setupNavButtons()

        getForeignUser()


        ib_follow.setOnClickListener(){
            followProfile()
        }


        ib_following.setOnClickListener(){
            unfollowProfile()
        }


        setupUserPostAdapter()
        setupGetUserPosts()

        setupGetFollowing()
        setupGetFollowers()

    }

    private fun followProfile() {


        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_follow)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.show()

        dialog.tv_follow.text="Are you sure you want to follow "+username+"?"

        dialog.btn_close_dialog_f.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.btn_confirm_follow.setOnClickListener(){

            val context:Context=this

            val addFollowRequest= AddFollowRequest(
                username = username
            )

            apiClient.getUserService(context).follow(addFollowRequest).enqueue(object : Callback<MessageResponse>{
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        ib_follow.isGone=true
                        ib_following.isGone=false
                        tv_follow_ing.text="Following"
                    }
                    else
                    {
                        Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                    }

                    dialog.dismiss()

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                    Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            })

        }


    }


    private fun unfollowProfile() {


        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_unfollow)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.show()

        dialog.tv_unfollow.text="Are you sure you want to unfollow "+username+"?"

        dialog.btn_close_dialog_uf.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.btn_confirm_unfollow.setOnClickListener()
        {

            val context:Context=this
            apiClient.getUserService(context).unfollow(username).enqueue(object : Callback<MessageResponse>{
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        ib_follow.isGone=false
                        ib_following.isGone=true
                        tv_follow_ing.text="Follow"
                    }

                    else
                    {
                        Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                    }
                    dialog.dismiss()

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            })

        }


    }

    private fun getForeignUser() {

        val context: Context =this
        apiClient.getUserService(context).getForeignUser(username).enqueue(object :
            Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if(response.isSuccessful)
                {
                    tv_name1_fg.text=response.body()!!.firstName
                    tv_name2_fg.text=response.body()!!.lastName

                    followers_count_fg.text = response.body()!!.followingCount.toString()
                    following_count_fg.text = response.body()!!.followersCount.toString()



                    val picture=response.body()!!.profileImage
                    if(picture!=null)
                    {
                        Picasso.get().load(UtilityFunctions.getFullImagePath(picture)).into(shapeableImageView_fg)
                    }


                    apiClient.getUserService(context).getFollow(username).enqueue(object :
                        Callback<MessageResponse> {
                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {

                            if(response.isSuccessful)
                            {

                                if(response.body()!!.message=="True")
                                {
                                    ib_follow.isGone=true
                                    ib_following.isGone=false
                                    tv_follow_ing.text="Following"
                                }

                                else
                                {

                                    ib_follow.isGone=false
                                    ib_following.isGone=true
                                    tv_follow_ing.text="Follow"
                                }

                            }

                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable)
                        {

                            Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()
                        }

                    })

                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()
            }

        })

    }


    private fun setupUserPostAdapter() {
        gvForeignPostAdapter = gvForeignPostAdapter(mutableListOf(),this)
        gv_fg_user_posts.adapter = gvForeignPostAdapter
    }


    private fun setupGetUserPosts() {


        apiClient.getPostService(this).getUserPosts(username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {

                        post_number_fg.text=response.body()!!.size.toString()

                        if(response.body()?.size == 0)
                            return

                        Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(coverImage_fg)

                        gvForeignPostAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })


    }


    private fun setupGetFollowers() {

        ll_followers_fg.setOnClickListener(){

            var followList = FollowList(
                username = username,
                type = false
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);
        }

    }

    private fun setupGetFollowing() {

        ll_following_fg.setOnClickListener(){

            var followList = FollowList(
                username = username,
                type = true
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }


    private fun setupNavButtons() {
        setupHome()
        setupAddPost()
        setupButtonMessages()
    }

    private fun setupHome() {
        btn_home_fg.setOnClickListener(){
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAddPost() {
        btn_newPost_fg.setOnClickListener(){
            val intent = Intent (this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupButtonMessages() {
        btn_messages_fg.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }
    }

}