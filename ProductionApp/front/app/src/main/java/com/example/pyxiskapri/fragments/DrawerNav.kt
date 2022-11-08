package com.example.pyxiskapri.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.activities.UserProfileActivity
import com.example.pyxiskapri.models.UserData
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.fragment_drawer_nav.*
import kotlinx.android.synthetic.main.fragment_drawer_nav.view.*


class DrawerNav : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.fragment_drawer_nav, container, false)

        updateDrawerData(view)
        setDrawerButtons(view)

        return view
    }

    public fun updateDrawerData(view: View){
        //iv_userAvatar.setImageBitmap()
        view.apply {
            val userData: UserData? = SessionManager(requireContext()).fetchUserData()
            if (userData != null) {
                tv_username.text = userData.username
                tv_fullName.text = userData.firstName + " " + userData.lastName
            }
        }
    }

    public fun setDrawerButtons(view: View){
        view.apply {

            btn_closeMenu.setOnClickListener {
                hideDrawer()
            }

            btn_signOut.setOnClickListener {
                SessionManager(requireContext()).clearToken()
                hideDrawer()
                val intent = Intent(requireContext(), MainActivity::class.java);
                startActivity(intent)
            }

            btn_UserProfile.setOnClickListener {
                val intent = Intent (requireContext(), UserProfileActivity::class.java);
                startActivity(intent);
            }

        }
    }


    override fun onResume() {
        super.onResume()
        view?.let { updateDrawerData(it) }
    }

    public fun hideDrawer(){
        view.apply{
            fl_navContainer.translationX = (320f * requireContext().resources.displayMetrics.density + 0.5f)
        }
    }

    public fun showDrawer(){
        view.apply {
            fl_navContainer.translationX = 0f
        }
    }






}