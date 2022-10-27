package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import kotlinx.android.synthetic.main.activity_home.*
import com.example.pyxiskapri.models.UserData
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setUserData()
        setLogoutTEMPButton()

        btn_testRequest.setOnClickListener{
            val context: Context = this
            apiClient.getUserService(context).test()
                .enqueue(object : Callback<MessageResponse> {
                    override fun onResponse( call: Call<MessageResponse>, response: Response<MessageResponse>) {
                        Log.d("", response.toString())
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Toast.makeText(context, "FAIL!", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }



    private fun setUserData(){
        val userData: UserData = sessionManager.fetchUserData() ?: return
        tv_username.text = userData.username
    }

    private fun setLogoutTEMPButton(){
        btn_logoutTEMP.setOnClickListener{
            sessionManager.clearToken()
            val intent = Intent (this, MainActivity::class.java);
            startActivity(intent);
        }
    }

}