package com.example.pyxiskapri.utility

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.example.pyxiskapri.models.UserData
import com.google.gson.Gson

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFS_PATH, Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun clearToken(){
        prefs.edit().remove(USER_TOKEN).commit()
    }

    fun saveToken(token: String){
        prefs.edit().putString(USER_TOKEN, token).commit()
    }

    fun fetchToken(): String?{
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserData(): UserData? {
        val token = fetchToken() ?: return null
        val payload = String(Base64.decode(token.split(".")[1], Base64.URL_SAFE))
        return Gson().fromJson(payload, UserData::class.java)
    }
}