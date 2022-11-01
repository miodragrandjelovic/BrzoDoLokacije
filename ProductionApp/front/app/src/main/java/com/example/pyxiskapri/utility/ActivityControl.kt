package com.example.pyxiskapri.utility

import android.content.Intent
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.pyxiskapri.activities.HomeActivity
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.models.UserData

public object ActivityControl {

    public fun handleUserSignedIn(context: Context, sessionManager: SessionManager, savedInstanceState: Bundle?){
        var user: UserData? = sessionManager.fetchUserData()
        if(user != null){
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(context, intent, savedInstanceState)
        }
    }

    public fun handleUserNotSignedIn(context: Context, sessionManager: SessionManager, savedInstanceState: Bundle?){
        var user: UserData? = sessionManager.fetchUserData()
        if(user == null){
            val intent = Intent(context, MainActivity::class.java)
            startActivity(context, intent, savedInstanceState)
        }
    }

}