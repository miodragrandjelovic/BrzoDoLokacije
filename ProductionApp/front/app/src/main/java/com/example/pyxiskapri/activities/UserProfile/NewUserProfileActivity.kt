package com.example.pyxiskapri.activities.UserProfile


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.*
import com.example.pyxiskapri.adapters.UserPostsAdapter
import com.example.pyxiskapri.custom_view_models.GradeDisplayView
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.dtos.response.StatisticsResponse
import com.example.pyxiskapri.models.ChangeCredentialsInformation
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import kotlinx.android.synthetic.main.popup_menu.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewUserProfileActivity : AppCompatActivity(){

    var flag=1

    lateinit var  window: PopupWindow
    lateinit var view :View


    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    lateinit var userPostAdapter:UserPostsAdapter

    var averageGrade: Double=0.0
    var numberOfPosts: Int = 0
    var differentLocations: Int = 0

    //lateinit var cover_image : String

    var changeCredentialsInformation = ChangeCredentialsInformation("","")

    override fun onRestart() {
        super.onRestart()
        setupGetUserPosts()
        setupGetUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_new_user_profile)

        view = layoutInflater.inflate(R.layout.popup_menu,null)
        window = PopupWindow(this)

        window.contentView = view

        popup()

        mapActivity()

        ///////////

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        setupUserPostAdapter()
        setupGetUserPosts()

        setupGetFollowing()
        setupGetFollowers()

        setupSetStatistics()

  }

    private fun setupSetStatistics() {

        ll_posts.setOnClickListener(){

            tv_statistics.setTextColor(Color.WHITE)
            tv_posts.setTextColor(Color.parseColor("#CC2045"))

            gv_n_user_posts.isGone=false
            cl_statistics.isGone=true

        }

        ll_statistics.setOnClickListener(){


            tv_statistics.setTextColor(Color.parseColor("#CC2045"))
            tv_posts.setTextColor(Color.WHITE)

            gv_n_user_posts.isGone=true
            cl_statistics.isGone=false

            average_grade.text=averageGrade.toString()
            post_number_statistics.text=numberOfPosts.toString() + " posts"

            if(averageGrade == 0.00)
                emoji.setImageResource(R.drawable.emoji_unset)
            else if(averageGrade < 1.5)
                emoji.setImageResource(R.drawable.emoji_crying)
            else if(averageGrade < 2.5)
                emoji.setImageResource(R.drawable.emoji_sad)
            else if(averageGrade < 3.5)
                emoji.setImageResource(R.drawable.emoji_neutral)
            else if(averageGrade < 4.5)
                emoji.setImageResource(R.drawable.emoji_happy)
            else if(averageGrade <= 5.0)
                emoji.setImageResource(R.drawable.emoji_amazed)


            var username = SessionManager(this).fetchUserData()?.username
            apiClient.getPostService(this).getUserTopPosts(username!!).enqueue(object : Callback<ArrayList<StatisticsResponse>>{
                override fun onResponse(call: Call<ArrayList<StatisticsResponse>>,response: Response<ArrayList<StatisticsResponse>>) {

                    if(response.isSuccessful && response.body() != null){
                        var size = response.body()!!.size

                        if(size>0)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(iv_coverImage_prvi)
                            gradeDisplay_followed_prvi.setupForFollowed()
                            gradeDisplay_followed_prvi.setGradeDisplay(response.body()!![0].averageGrade,response.body()!![0].gradesCount)

                        }
                        else
                        {
                            ll_prvi_grade.isGone=true
                            tv_no_post_prvi.isVisible=true
                        }

                        //drugi
                        if(size>1)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![1].coverImage)).into(iv_coverImage_drugi)
                            gradeDisplay_followed_drugi.setupForFollowed()
                            gradeDisplay_followed_drugi.setGradeDisplay(response.body()!![1].averageGrade,response.body()!![1].gradesCount)
                        }
                        else
                        {
                            ll_drugi_grade.isGone=true
                            tv_no_post_drugi.isVisible=true
                        }

                        //treci

                        if(size>2)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![2].coverImage)).into(iv_coverImage_treci)
                            gradeDisplay_followed_treci.setupForFollowed()
                            gradeDisplay_followed_treci.setGradeDisplay(response.body()!![2].averageGrade,response.body()!![2].gradesCount)

                        }
                        else
                        {
                            ll_treci_grade.isGone=true
                            tv_no_post_treci.isVisible=true
                        }
                    }



                }

                override fun onFailure(call: Call<ArrayList<StatisticsResponse>>, t: Throwable) {

                }

            })

        }


    }

    private fun setupGetUser() {

        var context:Context=this

        apiClient.getUserService(context).getUser()
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        tv_name1.text=response.body()!!.firstName
                        tv_name2.text=response.body()!!.lastName

                        followers_count.text = response.body()!!.followingCount.toString()
                        following_count.text = response.body()!!.followersCount.toString()

                        averageGrade=response.body()!!.averageGrade
                        differentLocations=response.body()!!.differentLocations


                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(picture)).into(shapeableImageView)
                        }


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong!", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun setupUserPostAdapter() {
        userPostAdapter = UserPostsAdapter(mutableListOf(),this, ::onPostDelete)
        gv_n_user_posts.adapter = userPostAdapter
    }

    private fun onPostDelete(){
        post_number.text = (post_number.text.toString().toInt() - 1).toString()
    }


    private fun setupGetUserPosts() {

        var user=sessionManager.fetchUserData()

        apiClient.getPostService(this).getUserPosts(user!!.username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {

                        post_number.text=response.body()!!.size.toString()
                        numberOfPosts=response.body()!!.size

                        changeCredentialsInformation.postsNumber=response.body()!!.size.toString()

                        if(response.body()?.size == 0)
                            return

                        var cover_image = response.body()!![0].coverImage
                        changeCredentialsInformation.coverImage=cover_image
                        Picasso.get().load(UtilityFunctions.getFullImagePath(cover_image)).into(coverImage)

                        userPostAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })


    }


    private fun setupGetFollowers() {

        ll_followers_u.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = false
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }

    private fun setupGetFollowing() {

        ll_following_u.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = true
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);
        }

    }



    private fun mapActivity() {
        ll_map.setOnClickListener(){

            val intent = Intent (this, MapUserPostActivity::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("averageGrade",averageGrade)
            startActivity(intent);

        }
    }



    private fun popup() {
        menu_btn.setOnClickListener() {

            view.settings.setOnClickListener() {

                val intent = Intent(this, ChangeCredentialsActivity::class.java);
                ActivityTransferStorage.changeCredentialsInformation = changeCredentialsInformation
                startActivity(intent);
            }

            view.logout.setOnClickListener() {
                SessionManager(this).clearToken()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            if (flag==-1) {
                window.dismiss()
            }
            else
            {
                window.showAsDropDown(menu_btn)
            }

            flag*=-1
        }
    }
}
