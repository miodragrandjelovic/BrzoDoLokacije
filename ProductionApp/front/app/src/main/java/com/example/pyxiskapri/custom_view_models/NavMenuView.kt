package com.example.pyxiskapri.custom_view_models

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ChatMainActivity
import com.example.pyxiskapri.activities.HomeActivity
import com.example.pyxiskapri.activities.MapActivity
import com.example.pyxiskapri.activities.NewPostActivity
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.utility.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_menu.view.*


class NavMenuView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.nav_menu, this, true)

        handleInput()
    }

    private fun handleInput(){
        btn_newPost.setOnClickListener {
            val intent = Intent (context, NewPostActivity::class.java);
            context.startActivity(intent)
        }

        // MAPS
        btn_discover.setOnClickListener {
            val intent = Intent (context, MapActivity::class.java);
            context.startActivity(intent)
        }

        // HOME
        btn_home.setOnClickListener {
            val intent = Intent (context, HomeActivity::class.java);
            context.startActivity(intent)
        }

        // MESSAGES
        btn_messages.setOnClickListener {
            val intent = Intent (context, ChatMainActivity::class.java);
            context.startActivity(intent)
        }

        // MESSAGES

        // NOTIFICATIONS
    }

    public fun setIndicator(indicator: Constants.NavIndicators){
        when(indicator.ordinal){
            0 -> btn_newPost.setColorFilter(ContextCompat.getColor(context, R.color.red))
            1 -> btn_discover.setColorFilter(ContextCompat.getColor(context, R.color.red))
            2 -> btn_home.setColorFilter(ContextCompat.getColor(context, R.color.red))
            3 -> btn_messages.setColorFilter(ContextCompat.getColor(context, R.color.red))
            4 -> btn_notifications.setColorFilter(ContextCompat.getColor(context, R.color.red))
        }
    }


}