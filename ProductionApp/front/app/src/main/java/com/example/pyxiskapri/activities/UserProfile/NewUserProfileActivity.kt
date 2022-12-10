package com.example.pyxiskapri.activities.UserProfile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.*
import com.example.pyxiskapri.adapters.UserPostsAdapter
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.ChangeCredentialsInformation
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import kotlinx.android.synthetic.main.activity_new_user_profile.btn_home
import kotlinx.android.synthetic.main.activity_new_user_profile.btn_messages
import kotlinx.android.synthetic.main.activity_new_user_profile.btn_newPost
import kotlinx.android.synthetic.main.activity_new_user_profile.tv_name1
import kotlinx.android.synthetic.main.activity_new_user_profile.tv_name2
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
        setupNavButtons()

        mapActivity()

        ///////////

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        setupUserPostAdapter()
        setupGetUserPosts()

        setupGetFollowing()
        setupGetFollowers()

  }

    private fun setupNavButtons() {
        setupHome()
        setupAddPost()
        setupButtonMessages()
        setupDiscover()
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



    private fun setupHome() {
        btn_home.setOnClickListener(){
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAddPost() {
        btn_newPost.setOnClickListener(){
            val intent = Intent (this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupButtonMessages() {
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupDiscover(){
        btn_discoverr.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            startActivity(intent);
        }
    }

}
