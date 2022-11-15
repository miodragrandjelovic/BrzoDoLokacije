package com.example.pyxiskapri.utility

import android.app.Activity
import android.content.Intent
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import com.example.pyxiskapri.activities.HomeActivity
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.models.UserData

public object ActivityControl {

    public fun handleUserSignedIn(activity: Activity?, context: Context, sessionManager: SessionManager, savedInstanceState: Bundle?){
        var user: UserData? = sessionManager.fetchUserData()
        if(user != null){
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.finishAffinity()
            startActivity(context, intent, savedInstanceState)
        }
    }

    public fun handleUserNotSignedIn(activity: Activity?, context: Context, sessionManager: SessionManager, savedInstanceState: Bundle?){
        var user: UserData? = sessionManager.fetchUserData()
        if(user == null){
            val intent = Intent(context, MainActivity::class.java)
            startActivity(context, intent, savedInstanceState)
        }
    }

}