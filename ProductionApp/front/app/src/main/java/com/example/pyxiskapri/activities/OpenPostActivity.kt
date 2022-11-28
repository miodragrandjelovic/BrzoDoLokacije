package com.example.pyxiskapri.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.CommentAdapter
import com.example.pyxiskapri.adapters.PostImagesAdapter
import com.example.pyxiskapri.dtos.request.NewCommentRequest
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.*
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_open_post.*
import kotlinx.android.synthetic.main.activity_open_post.btn_home
import kotlinx.android.synthetic.main.activity_open_post.btn_newPost
import kotlinx.android.synthetic.main.dialog_full_image.*
import kotlinx.android.synthetic.main.item_post_image.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OpenPostActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    private lateinit var postData: PostListItem
    private lateinit var postLocation: LatLng

    private lateinit var postImagesAdapter: PostImagesAdapter

    private lateinit var commentsAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_post)

        postData = ActivityTransferStorage.postItemToOpenPost

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupNavButtons()
        setupPostButtons()

        requestPostData()

        setupOpenMapButton()

        ll_user_btn.setOnClickListener(){
            val intent = Intent(this, ForeignProfileActivity::class.java)
            intent.putExtra("username", tv_ownerUsername.text.toString())
            this.startActivity(intent)
            finish()
        }

        iv_ownerAvatar.setOnClickListener()
        {
            val intent = Intent(this, ForeignProfileActivity::class.java)
            intent.putExtra("username", tv_ownerUsername.text.toString())
            this.startActivity(intent)
            finish()
        }

        setupComments()

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
            finish()
        }
    }

    private fun setupButtonHome(){
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
            finish()
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
            postLocation = LatLng(postAdditionalData.latitude, postAdditionalData.longitude)

            postImagesAdapter = PostImagesAdapter(postAdditionalData.additionalImages)
            rv_additionalImages.adapter = postImagesAdapter
            rv_additionalImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        }

        Picasso.get().load(UtilityFunctions.getFullImagePath(postData.ownerImage)).into(iv_ownerAvatar)

        tv_ownerUsername.text = postData.ownerUsername

        Picasso.get().load(UtilityFunctions.getFullImagePath(postData.coverImage)).into(iv_coverImage)

        iv_coverImage.setOnClickListener {
            val dialog = Dialog(this)

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_full_image)
            dialog.window?.attributes = layoutParams

            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            dialog.show()

            Picasso.get().load(UtilityFunctions.getFullImagePath(postData.coverImage)).into(dialog.iv_fullImage)

            dialog.btn_closeDialog.setOnClickListener {
                dialog.dismiss()
            }
        }

        tv_likeCount.text = postData.likeCount.toString()
        tv_viewCount.text = postData.viewCount.toString()

        // Liked
        if(postData.isLiked)
            iv_likeIcon.setColorFilter(ContextCompat.getColor(this, R.color.gold), PorterDuff.Mode.SRC_IN);
        else
            iv_likeIcon.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        // REPORT CHECK
    }

    private fun setupOpenMapButton(){
        btn_openMap.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            ActivityTransferStorage.openPostToMap = LatLng(postLocation.latitude, postLocation.longitude)
            startActivity(intent);
        }
    }



    private fun setupComments(){
        commentsAdapter = CommentAdapter(arrayListOf(), this)
        elv_comments.setAdapter(commentsAdapter)

        requestComments()

        btn_postComment.setOnClickListener{
            postNewComment()
        }
    }

    private fun requestComments(){
        apiClient.getCommentService(this).getPostComments(postData.id)
            .enqueue(object : Callback<ArrayList<CommentResponse>> {
                override fun onResponse(call: Call<ArrayList<CommentResponse>>, response: Response<ArrayList<CommentResponse>>) {
                    if(response.isSuccessful) {
                        commentsAdapter.AddCommentList(response.body()!!)
                    }

                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Log.d("ERROR", "Bad Request")

                }

                override fun onFailure(call: Call<ArrayList<CommentResponse>>, t: Throwable) {
                    Log.d(
                        "OpenPostActivity",
                        "Nije implementiran onFailure za getCommetns api zahtev!"
                    )
                }

            }
        )
    }

    private fun postNewComment(){

        if(et_newCommentText.text.toString() == "") {
            // Obavestenje da mora da unese tekst ili da pocrveni okvir
            return
        }



        var newComment = NewCommentRequest(
            postId = postData.id,
            commentText = et_newCommentText.text.toString()
        )

        apiClient.getCommentService(this).addNewComment(newComment)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful) {
                        et_newCommentText.text.clear()
                        // Uradi nesto
                    }

                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Log.d("ERROR", "Bad Request")

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(
                        "OpenPostActivity",
                        "Nije implementiran onFailure za addNewComment api zahtev!"
                    )
                }
            }
        )
    }

}