package com.example.pyxiskapri.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
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
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.finishAffinity()
                startActivity(intent)
            }

            btn_UserProfile.setOnClickListener {
                val intent = Intent (requireContext(), NewUserProfileActivity::class.java)
                startActivity(intent);
            }

        }
    }


    override fun onResume() {
        super.onResume()
        hideDrawer()
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