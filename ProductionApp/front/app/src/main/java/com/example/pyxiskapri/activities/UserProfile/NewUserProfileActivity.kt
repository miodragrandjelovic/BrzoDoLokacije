package com.example.pyxiskapri.activities.UserProfile


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.HomeActivity
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.activities.NewPostActivity
import com.example.pyxiskapri.adapters.UserPostsAdapter
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import kotlinx.android.synthetic.main.activity_new_user_profile.tv_name1
import kotlinx.android.synthetic.main.activity_new_user_profile.tv_name2
import kotlinx.android.synthetic.main.activity_user_profile.*
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

    override fun onRestart() {
        super.onRestart()
        setupGetUserPosts()
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



  }

    private fun setupNavButtons() {
        setupHome()
        setupAddPost()
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


                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
                            shapeableImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
                        }


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong!", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun setupUserPostAdapter() {
        userPostAdapter = UserPostsAdapter(mutableListOf(),this)
        gv_n_user_posts.adapter = userPostAdapter
    }


    private fun setupGetUserPosts() {

        var user=sessionManager.fetchUserData()

        apiClient.getPostService(this).getUserPosts(user!!.username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {

                        post_number.text=response.body()!!.size.toString()
                        userPostAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })


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

}
