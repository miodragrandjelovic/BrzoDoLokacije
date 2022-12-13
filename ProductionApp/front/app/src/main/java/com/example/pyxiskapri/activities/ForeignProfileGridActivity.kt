package com.example.pyxiskapri.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.gvForeignPostAdapter
import com.example.pyxiskapri.dtos.request.AddFollowRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.dtos.response.StatisticsResponse
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.*
import kotlinx.android.synthetic.main.activity_new_user_profile.*
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


    var averageGrade: Double=0.0
    var numberOfPosts: Int = 0

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
            intent.putExtra("averageGrade", averageGrade)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent);
        }

        val bundle = intent.extras
        username = bundle?.getString("username")!!

        apiClient= ApiClient()
        sessionManager= SessionManager(this)

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

        setupSetStatistics()

    }

    private fun setupSetStatistics() {
        ll_posts_f.setOnClickListener(){

            tv_statistics_f.setTextColor(Color.WHITE)
            tv_posts_f.setTextColor(Color.parseColor("#CC2045"))

            gv_fg_user_posts.isGone=false
            cl_statistics_f.isGone=true

        }

        ll_statistics_f.setOnClickListener(){


            tv_statistics_f.setTextColor(Color.parseColor("#CC2045"))
            tv_posts_f.setTextColor(Color.WHITE)

            gv_fg_user_posts.isGone=true
            cl_statistics_f.isGone=false

            average_grade_f.text=averageGrade.toString()
            post_number_statistics_f.text=numberOfPosts.toString() + " posts"

            if(averageGrade == 0.00)
                emoji_f.setImageResource(R.drawable.emoji_unset)
            else if(averageGrade < 1.5)
                emoji_f.setImageResource(R.drawable.emoji_crying)
            else if(averageGrade < 2.5)
                emoji_f.setImageResource(R.drawable.emoji_sad)
            else if(averageGrade < 3.5)
                emoji_f.setImageResource(R.drawable.emoji_neutral)
            else if(averageGrade < 4.5)
                emoji_f.setImageResource(R.drawable.emoji_happy)
            else if(averageGrade <= 5.0)
                emoji_f.setImageResource(R.drawable.emoji_amazed)


            apiClient.getPostService(this).getUserTopPosts(username).enqueue(object : Callback<ArrayList<StatisticsResponse>>{
                override fun onResponse(call: Call<ArrayList<StatisticsResponse>>, response: Response<ArrayList<StatisticsResponse>>) {
                    if(response.isSuccessful && response.body() != null){
                        var size = response.body()!!.size

                        if(size>0)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(iv_coverImage_prvi_f)
                            gradeDisplay_followed_prvi_f.setupForFollowed()
                            gradeDisplay_followed_prvi_f.setGradeDisplay(response.body()!![0].averageGrade,response.body()!![0].gradesCount)

                        }
                        else
                        {
                            ll_prvi_grade_f.isGone=true
                            tv_no_post_prvi_f.isVisible=true
                        }

                        //drugi
                        if(size>1)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![1].coverImage)).into(iv_coverImage_drugi_f)
                            gradeDisplay_followed_drugi_f.setupForFollowed()
                            gradeDisplay_followed_drugi_f.setGradeDisplay(response.body()!![1].averageGrade,response.body()!![1].gradesCount)
                        }
                        else
                        {
                            ll_drugi_grade_f.isGone=true
                            tv_no_post_drugi_f.isVisible=true
                        }

                        //treci

                        if(size>2)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![2].coverImage)).into(iv_coverImage_treci_f)
                            gradeDisplay_followed_treci_f.setupForFollowed()
                            gradeDisplay_followed_treci_f.setGradeDisplay(response.body()!![2].averageGrade,response.body()!![2].gradesCount)

                        }
                        else
                        {
                            ll_treci_grade_f.isGone=true
                            tv_no_post_treci_f.isVisible=true
                        }
                    }



                }

                override fun onFailure(call: Call<ArrayList<StatisticsResponse>>, t: Throwable) {

                }

            })

        }
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

                        var number = followers_count_fg.text.toString().toInt()
                        number += 1
                        followers_count_fg.text=number.toString()

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

                        var number = followers_count_fg.text.toString().toInt()
                        number -= 1
                        followers_count_fg.text=number.toString()

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

                    averageGrade= response.body()!!.averageGrade


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

                        numberOfPosts=response.body()!!.size

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

}