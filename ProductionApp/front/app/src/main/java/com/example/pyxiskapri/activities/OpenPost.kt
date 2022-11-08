package com.example.pyxiskapri.activities

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.PostFullResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.activity_open_post.*
import kotlinx.android.synthetic.main.activity_open_post.view.*
import kotlinx.android.synthetic.main.item_post.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenPost : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    private var postId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_post)
        postId = savedInstanceState!!.getInt("postId")

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

    }

    private fun requestPostData(){
        apiClient.getPostService(this).getPostById(postId)
            .enqueue(object : Callback<PostFullResponse> {
                override fun onResponse(call: Call<PostFullResponse>, response: Response<PostFullResponse>) {
                    if(response.isSuccessful)
                        updatePostData(response.body()!!)


                }

                override fun onFailure(call: Call<PostFullResponse>, t: Throwable) {

                }

            })
    }

    private fun updatePostData(postData: PostFullResponse){
        iv_ownerAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(postData.ownerImage))

        tv_ownerUsername.text = postData.ownerUsername
        tv_postDescription.text = postData.postDescription

        iv_ownerAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(postData.coverImage))

        // ADDITIONAL IMAGES

        tv_likeCount.text = postData.likeCount.toString()
        tv_viewCount.text = postData.viewCount.toString()

        // LIKE CHECK

        // DISLIKE CHECK

        // FOLLOW CHECK

        // REPORT CHECK


        // COMMENT SETUP

    }

}