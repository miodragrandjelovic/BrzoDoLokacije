package com.example.pyxiskapri.activities

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.ForeignUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_foreign_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.fcv_drawerNavUserProfile
import kotlinx.android.synthetic.main.activity_user_profile.tv_name1
import kotlinx.android.synthetic.main.activity_user_profile.tv_name2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForeignProfileActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager
    lateinit var username:String

    override fun onRestart() {
        super.onRestart()
        getForeignUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        val bundle = intent.extras
        username = bundle?.getString("username")!!

        Log.d("",username)


        getForeignUser()



    }

    private fun getForeignUser() {

        val foreignUser= ForeignUserRequest(
            username = username
        )
        val context:Context=this
        apiClient.getUserService(context).getForeignUser(foreignUser).enqueue(object : Callback<GetUserResponse>{
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


}