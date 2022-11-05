package com.example.pyxiskapri.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.models.BasicUserData
import com.example.pyxiskapri.models.UserData
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.fragment_drawer_nav.fl_navContainer
import kotlinx.android.synthetic.main.fragment_drawer_nav.view.*


class DrawerNav : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.fragment_drawer_nav, container, false)

        var sessionManager: SessionManager = SessionManager(requireContext())

        view.apply {

            //iv_userAvatar.setImageBitmap()
            var userData: UserData? = sessionManager.fetchUserData()

            if(userData != null) {
                tv_username.text = userData.username
                tv_fullName.text = userData.firstName + " " + userData.lastName
            }

            btn_closeMenu.setOnClickListener {
                fl_navContainer.translationX = (320f * requireContext().resources.displayMetrics.density + 0.5f)
            }

            btn_signOut.setOnClickListener {
                sessionManager.clearToken()
                val intent = Intent(requireContext(), MainActivity::class.java);
                startActivity(intent)
            }
        }



        return view
    }

    public fun showDrawer(){
        view.apply {
            fl_navContainer.translationX = 0f
        }
    }






}