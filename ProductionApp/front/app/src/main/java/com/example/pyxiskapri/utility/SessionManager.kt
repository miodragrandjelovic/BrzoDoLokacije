package com.example.pyxiskapri.utility

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.example.pyxiskapri.models.UserData
import com.google.gson.Gson

class SessionManager(private var context: Context) {
    companion object {
        const val USER_TOKEN = "user_token"

        const val USERNAME_KEY = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name"
        const val FIRSTNAME_KEY = "FirstName"
        const val LASTNAME_KEY = "LastName"
        const val EMAIL_KEY = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"
        const val PROFILE_IMAGE_KEY = "ImagePath"
        const val ROLE_KEY = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"
        const val EXP_KEY = "exp"
    }

    fun clearToken(){
        context.getSharedPreferences(Constants.SHAREDPREFS_PATH, Context.MODE_PRIVATE).edit().remove(USER_TOKEN).commit()
    }

    fun saveToken(token: String){
        context.getSharedPreferences(Constants.SHAREDPREFS_PATH, Context.MODE_PRIVATE).edit().putString(USER_TOKEN, token).commit()
    }

    fun fetchToken(): String?{
        return context.getSharedPreferences(Constants.SHAREDPREFS_PATH, Context.MODE_PRIVATE).getString(USER_TOKEN, null)
    }

    fun fetchUserData(): UserData? {
        val token = fetchToken() ?: return null
        val payload = String(Base64.decode(token.split(".")[1], Base64.URL_SAFE))
        return Gson().fromJson(payload, UserData::class.java)
    }
}