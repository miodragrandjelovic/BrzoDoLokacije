package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.activity_home.*
import android.content.SharedPreferences


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUserData()
        setLogoutTEMPButton()
    }



    private fun setUserData(){
        val prefs= getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val username: String? = prefs.getString("username", "_no_username_in_prefs_")

        tv_username.text = username
    }

    private fun setLogoutTEMPButton(){
        btn_logoutTEMP.setOnClickListener{
            clearUserPrefs(getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE))
            val intent = Intent (this, MainActivity::class.java);
            startActivity(intent);
        };
    }

    private fun clearUserPrefs(prefs: SharedPreferences){
        val editor: SharedPreferences.Editor = prefs.edit()
        editor
            .remove("token")
            .remove("username")
            .remove("email")
            .commit()
    }






}