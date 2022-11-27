package com.example.pyxiskapri.activities.UserProfile

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.MainActivity
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_map_user_post.*
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import kotlinx.android.synthetic.main.activity_new_user_profile.menu_btn
import kotlinx.android.synthetic.main.popup_menu.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MapUserPostActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    /////////////////////////////////

    var flag=1

    lateinit var  window: PopupWindow
    lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_user_post)

        setupMap()

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        /////////////////


        view = layoutInflater.inflate(R.layout.popup_menu,null)
        window = PopupWindow(this)

        window.contentView = view

        popup()

        postsActivity()



    }

    private fun postsActivity() {
        ll_posts.setOnClickListener(){

            val intent = Intent (this, NewUserProfileActivity::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent);

        }
    }



    private fun setupGetUser() {

        var context: Context =this

        apiClient.getUserService(context).getUser()
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        tv_name1_m.text=response.body()!!.firstName
                        tv_name2_m.text=response.body()!!.lastName


                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
                            shapeableImageView_m.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
                        }


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong!", Toast.LENGTH_LONG).show()
                }

            })
    }


    private fun popup() {
        menu_btn.setOnClickListener() {

            view.settings.setOnClickListener() {
                val intent = Intent(this, ChangeCredentialsActivity::class.java);
                startActivity(intent);
            }

            view.logout.setOnClickListener() {
                SessionManager(this).clearToken()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            if (flag==-1) {
                window.dismiss()
            }
            else
            {
                window.showAsDropDown(menu_btn)
            }

            flag*=-1
        }
    }


    private fun setupMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.user_post_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        geocoder = Geocoder(this, Locale.getDefault())
    }

}