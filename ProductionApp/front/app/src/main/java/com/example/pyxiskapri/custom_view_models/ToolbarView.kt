package com.example.pyxiskapri.custom_view_models

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.models.UserData
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar.view.*


class ToolbarView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    private var userData: UserData?
    private var profileClickListener: ProfileClickListener

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.toolbar, this, true)

        profileClickListener = ProfileClickListener(context)
        userData = SessionManager(context).fetchUserData()

        handleInput()
        setupToolbarViews()
    }

    private class ProfileClickListener(var context: Context) : OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent (context, NewUserProfileActivity::class.java)
            context.startActivity(intent);
        }
    }

    private fun handleInput(){
        btn_toolbarUserAvatar.setOnClickListener(profileClickListener)
        tv_fullname.setOnClickListener(profileClickListener)
        tv_username.setOnClickListener(profileClickListener)
    }

    private fun setupToolbarViews(){
        if(userData == null)
            return
        Picasso.get().load(UtilityFunctions.getFullImagePath(userData!!.profileImagePath)).into(btn_toolbarUserAvatar)
        tv_fullname.text = StringBuilder().append(userData!!.firstName).append(" ").append(userData!!.lastName)
        tv_username.text = StringBuilder().append("@").append(userData!!.username)
    }


}