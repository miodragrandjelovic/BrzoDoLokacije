package com.example.pyxiskapri.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.adapters.CommentAdapter
import com.example.pyxiskapri.adapters.PostImagesAdapter
import com.example.pyxiskapri.adapters.TagsDisplayAdapter
import com.example.pyxiskapri.dtos.request.NewCommentRequest
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_open_post.*
import kotlinx.android.synthetic.main.activity_open_post.btn_addTag
import kotlinx.android.synthetic.main.activity_open_post.btn_discover
import kotlinx.android.synthetic.main.activity_open_post.btn_home
import kotlinx.android.synthetic.main.activity_open_post.btn_messages
import kotlinx.android.synthetic.main.activity_open_post.btn_newPost
import kotlinx.android.synthetic.main.activity_open_post.iv_coverImage
import kotlinx.android.synthetic.main.activity_open_post.rv_images
import kotlinx.android.synthetic.main.activity_open_post.rv_tags
import kotlinx.android.synthetic.main.dialog_full_image.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class OpenPostActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    private lateinit var postData: PostResponse
    private lateinit var postLocation: LatLng

    private lateinit var postImagesAdapter: PostImagesAdapter

    private lateinit var tagsAdapter: TagsDisplayAdapter

    private lateinit var commentsAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_post)

        postData = ActivityTransferStorage.postItemToOpenPost

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupNavButtons()

        setupTagsAdapter()

        requestPostData()

        setupOpenMapButton()

        btn_openMap.setOnClickListener(){
            val intent = Intent(this, ForeignProfileGridActivity::class.java)
            intent.putExtra("username", tv_ownerUsername.text.toString())
            this.startActivity(intent)
            finish()
        }

        iv_ownerAvatar.setOnClickListener()
        {
            if(tv_ownerUsername.text.toString()==SessionManager(this).fetchUserData()?.username)
            {
                val intent = Intent(this, NewUserProfileActivity::class.java)
                this.startActivity(intent)
                finish()
            }
            else
            {
                val intent = Intent(this, ForeignProfileGridActivity::class.java)
                intent.putExtra("username", tv_ownerUsername.text.toString())
                this.startActivity(intent)
                finish()
            }

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
        // NEW POST
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }

        // MAPS
        btn_discover.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            startActivity(intent);
        }

        // HOME
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }

        // MESSAGES
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }

        // NOTIFICATIONS
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

    private fun setupTagsAdapter(){
        tagsAdapter = TagsDisplayAdapter(context = this)
        rv_tags.adapter = tagsAdapter
        rv_tags.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
    }

    private fun updatePostData(postAdditionalData: PostAdditionalData?){

        if(postAdditionalData != null){
            tv_postDescription.text = postAdditionalData.postDescription
            postLocation = LatLng(postAdditionalData.latitude, postAdditionalData.longitude)

            tv_commentsCount.text = buildString {
                append(postAdditionalData.commentCount.toString())
                    .append(" comments") }

            Picasso.get().load(UtilityFunctions.getFullImagePath(sessionManager.fetchUserData()!!.profileImagePath)).into(iv_userNewCommentAvatar)

            postImagesAdapter = PostImagesAdapter(postAdditionalData.additionalImages)
            rv_images.adapter = postImagesAdapter
            rv_images.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        }

        Picasso.get().load(UtilityFunctions.getFullImagePath(postData.ownerImage)).into(iv_ownerAvatar)

        tv_ownerUsername.text = postData.ownerUsername

        tv_postDate.text = postData.postDate

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

        if(postData.tagsString != null)
            tagsAdapter.setTags(postData.tagsString)

        gradeDisplay.setGradeDisplay(postData.averageGrade, postData.gradesCount)
        gradeSelector.setGradeSelectorInitialGrade(postData.usersGrade)
        gradeSelector.gradeDisplay = gradeDisplay
        gradeSelector.gradedPostId = postData.id
    }

    private fun setupOpenMapButton(){
        btn_openMap.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            ActivityTransferStorage.openPostToMap = LatLng(postLocation.latitude, postLocation.longitude)
            startActivity(intent);
        }
    }



    private fun setupComments(){
        commentsAdapter = CommentAdapter(arrayListOf(), postData.id, this)
        elv_comments.setAdapter(commentsAdapter)

        requestComments()

        btn_addTag.setOnClickListener{
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
            parentId = 0,
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