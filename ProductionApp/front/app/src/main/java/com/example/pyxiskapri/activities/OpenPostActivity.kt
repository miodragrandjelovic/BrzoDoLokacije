package com.example.pyxiskapri.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.activity_open_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenPostActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    private lateinit var postData: PostListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_post)




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            postData = intent.getSerializableExtra("postData", PostListItem::class.java)!!
        else
            postData = intent.getSerializableExtra("postData") as PostListItem



        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        requestPostData()
    }

    private fun requestPostData(){
        apiClient.getPostService(this).getPostById(postData.id)
            .enqueue(object : Callback<PostAdditionalData> {
                override fun onResponse(call: Call<PostAdditionalData>, response: Response<PostAdditionalData>) {
                    if(response.isSuccessful) {
                        updatePostData(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<PostAdditionalData>, t: Throwable) {

                }

            })
    }

    private fun updatePostData(postAdditionalData: PostAdditionalData){
        iv_ownerAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(postData.ownerImage))

        tv_ownerUsername.text = postData.ownerUsername
        tv_postDescription.text = postAdditionalData.postDescription

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