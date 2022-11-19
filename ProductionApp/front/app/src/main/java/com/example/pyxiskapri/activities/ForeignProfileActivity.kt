package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.gvForeignPostAdapter
import com.example.pyxiskapri.dtos.request.AddFollowRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_foreign_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.fcv_drawerNavUserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForeignProfileActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager
    lateinit var username:String

    lateinit var gvForeignPostAdapter: gvForeignPostAdapter

    override fun onRestart() {
        super.onRestart()
       // getForeignUser()
       // setupUserPostAdapter()
        setupGetUserPosts()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)


        val bundle = intent.extras
        username = bundle?.getString("username")!!

        Log.d("",username)

        setupUserPostAdapter()
        setupGetUserPosts()



        back_f.setOnClickListener()
        {

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        getForeignUser()



        ib_follow.setOnClickListener(){
            followProfile()
        }


        ib_following.setOnClickListener(){
            unfollowProfile()
        }



    }


    private fun followProfile() {


        val context:Context=this

        val addFollowRequest= AddFollowRequest(
            username = tv_f_username.text.toString()
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
                }
                else
                {
                    Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
            }

        })

    }


    private fun unfollowProfile() {

        val context:Context=this
        apiClient.getUserService(context).unfollow(tv_f_username.text.toString()).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {

                if(response.isSuccessful)
                {
                    ib_follow.isGone=false
                    ib_following.isGone=true
                }

                else
                {
                    Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
            }

        })


    }


    private fun getForeignUser() {

        val context:Context=this
        apiClient.getUserService(context).getForeignUser(username).enqueue(object : Callback<GetUserResponse>{
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if(response.isSuccessful)
                {
                    tv_f_first_name.text=response.body()!!.firstName
                    tv_f_last_name.text=response.body()!!.lastName
                    tv_f_username.text=response.body()!!.username
                    tv_f_email.text=response.body()!!.email

                    tv_f_name1.text=response.body()!!.firstName
                    tv_f_name2.text=response.body()!!.lastName

                    val picture=response.body()!!.profileImage
                    if(picture!=null)
                    {
                        var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
                        f_imageViewReal.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
                    }


                    apiClient.getUserService(context).getFollow(tv_f_username.text.toString()).enqueue(object : Callback<MessageResponse>{
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
                                }

                                else
                                {

                                    ib_follow.isGone=false
                                    ib_following.isGone=true
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

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavUserProfile.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupUserPostAdapter() {
        gvForeignPostAdapter = gvForeignPostAdapter(mutableListOf(),this)
        gv_f_user_posts.adapter = gvForeignPostAdapter
    }


    private fun setupGetUserPosts() {


        apiClient.getPostService(this).getUserPosts(username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {
                        gvForeignPostAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })


    }



}