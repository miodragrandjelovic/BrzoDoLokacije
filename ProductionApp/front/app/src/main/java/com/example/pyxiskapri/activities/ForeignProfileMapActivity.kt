package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pyxiskapri.R
import com.example.pyxiskapri.fragments.DrawerNav
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.*
import kotlinx.android.synthetic.main.activity_foreign_profile_map.*

class ForeignProfileMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile_map)

        ll_posts_fm.setOnClickListener(){
            val intent = Intent (this, ForeignProfileGridActivity::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent);
        }

        setupNavButtons()

    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNav_fm.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupNavButtons() {
        setupHome()
        setupAddPost()
        setupButtonMessages()
    }

    private fun setupHome() {
        btn_home_fm.setOnClickListener(){
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAddPost() {
        btn_newPost_fm.setOnClickListener(){
            val intent = Intent (this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupButtonMessages() {
        btn_messages_fm.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }
    }

}