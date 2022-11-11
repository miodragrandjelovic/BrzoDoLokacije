package com.example.pyxiskapri.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pyxiskapri.R
import com.example.pyxiskapri.fragments.DrawerNav
import kotlinx.android.synthetic.main.activity_user_profile.*

class ForeignProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile)
    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavUserProfile.getFragment<DrawerNav>().showDrawer()
    }


}