package com.example.pyxiskapri.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.*
import kotlinx.android.synthetic.main.activity_open_post.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenPostActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    lateinit var activityTransferStorage: ActivityTransferStorage

    private lateinit var postData: PostListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_post)

        //collectActivityPassedData()
        Log.d("postData", ActivityTransferStorage.postItemToOpenPost.toString())
        postData = ActivityTransferStorage.postItemToOpenPost


        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupNavButtons()
        setupPostButtons()
        requestPostData()
    }

    override fun onResume() {
        super.onResume()
        //collectActivityPassedData()
        updatePostData(null)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

//    private fun collectActivityPassedData(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            postData = intent.getSerializableExtra("postData", PostListItem::class.java)!!
//        else
//            postData = intent.getSerializableExtra("postData") as PostListItem
//    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavOpenPost.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupNavButtons(){
        setupButtonNewPost()
        setupButtonHome()
    }

    private fun setupButtonNewPost(){
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupButtonHome(){
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupPostButtons(){
        btn_like.setOnClickListener{
            setRemoveLike()
        }
    }

    private fun setRemoveLike(){
        if(postData.isLiked) {
            apiClient.getPostService(this).removeLike(postData.id)
                .enqueue(object : Callback<MessageResponse> {
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if(response.isSuccessful) {
                            postData.isLiked = false
                            postData.likeCount -= 1
                            updatePostData(null)
                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Log.d(
                            "OpenPostActivity",
                            "Nije implementiran onFailure za removeLike api zahtev!"
                        )
                    }

                })
        }
        else {
            apiClient.getPostService(this).setLike(postData.id)
                .enqueue(object : Callback<MessageResponse> {
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if (response.isSuccessful) {
                            postData.isLiked = true
                            postData.likeCount += 1
                            updatePostData(null)
                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Log.d(
                            "OpenPostActivity",
                            "Nije implementiran onFailure za setLike api zahtev!"
                        )
                    }

                })
        }
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
                    Log.d(
                        "OpenPostActivity",
                        "Nije implementiran onFailure za getPostById api zahtev!"
                    )
                }

            })
    }

    private fun updatePostData(postAdditionalData: PostAdditionalData?){

        if(postAdditionalData != null){
            tv_postDescription.text = postAdditionalData.postDescription
            //ADDITIONAL IMAGES
        }

        if(postData.ownerImage != null)
        {
            iv_ownerAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(postData.ownerImage))
            tv_ownerUsername.text = postData.ownerUsername
        }


        iv_coverImage.setImageBitmap(UtilityFunctions.base64ToBitmap(postData.coverImage))

        tv_likeCount.text = postData.likeCount.toString()
        tv_viewCount.text = postData.viewCount.toString()

        // Liked
        if(postData.isLiked)
            iv_likeIcon.setColorFilter(ContextCompat.getColor(this, R.color.gold), PorterDuff.Mode.SRC_IN);
        else
            iv_likeIcon.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        // REPORT CHECK


        // COMMENT SETUP

    }

}