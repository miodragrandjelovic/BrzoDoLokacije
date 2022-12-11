package com.example.pyxiskapri.activities.UserProfile

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.*
import com.example.pyxiskapri.dtos.response.CustomMarkerResponse
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_map_user_post.*
import kotlinx.android.synthetic.main.activity_new_user_profile.menu_btn
import kotlinx.android.synthetic.main.popup_menu.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MapUserPostActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    lateinit var mCustomMarkerView:View
    lateinit var mMarkerImageView: ImageView

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    private lateinit var markerImage: ImageView




    /////////////////////////////////

    var flag=1
    var mapFlag=0

    lateinit var  window: PopupWindow
    lateinit var view : View


    override fun onRestart() {
        super.onRestart()
        setupGetUser()
    }

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


        mCustomMarkerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

        markerImage = mCustomMarkerView.findViewById(R.id.marker_image)

        mMarkerImageView = mCustomMarkerView.findViewById(R.id.cover_image)


        setupGetFollowers()
        setupGetFollowing()

    }


    private fun setupMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.user_post_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))

        geocoder = Geocoder(this, Locale.getDefault())

        addCustomMarkerFromURL(true)

        map.setOnMarkerClickListener{marker ->

            var context = this

            var tag= marker.tag as? Int
            apiClient.getPostService(context).GetOnePostById(tag!!).enqueue(object : Callback<PostResponse>{
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    val intent = Intent(context, OpenPostActivity::class.java)
                    ActivityTransferStorage.postItemToOpenPost = response.body()!!
                    context.startActivity(intent)

                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
                }
            })

            true
        }

        map.setOnCameraMoveListener {
            val cameraPosition = googleMap.cameraPosition


            if (cameraPosition.zoom > 9.0 && mapFlag==0) {

                var layoutParams = ConstraintLayout.LayoutParams(248, 340)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(248, 248)
                mMarkerImageView.layoutParams = layoutParams


                map.clear()

                addCustomMarkerFromURL(false)


                mapFlag=1
            }
            else if(cameraPosition.zoom < 9.0 && mapFlag==1)
            {
                var layoutParams = ConstraintLayout.LayoutParams(138, 193)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(138, 138)
                mMarkerImageView.layoutParams = layoutParams


                map.clear()

                addCustomMarkerFromURL(false)


                mapFlag=0
            }


        }

    }



    private fun getMarkerBitmapFromView(view: View, bitmap: Bitmap?): Bitmap? {

        mMarkerImageView?.setImageBitmap(bitmap)

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable = view.background
        drawable?.draw(canvas)
        view.draw(canvas)
        return returnedBitmap
    }

    private fun addCustomMarkerFromURL(pom: Boolean) {
        if (map == null) {
            return
        }

        var context:Context = this

        var username = SessionManager(this).fetchUserData()?.username
        apiClient.getPostService(context).PostOnMap(username!!).enqueue(object : Callback<ArrayList<CustomMarkerResponse>>{
            override fun onResponse(
                call: Call<ArrayList<CustomMarkerResponse>>,
                response: Response<ArrayList<CustomMarkerResponse>>
            ) {
                if(response.isSuccessful) {
                    if(response.body() != null && response.body()?.size != 0){

                        if(pom) {

                            post_number_um.text = response.body()!!.size.toString()

                            var cameraLocation: LatLng =
                                LatLng(response.body()!![0].latitude, response.body()!![0].longitude)

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraLocation, 3f))

                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(coverImage_m)

                        }

                        post_number_um.text = response.body()!!.size.toString()
                        Picasso.get()
                            .load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage))
                            .into(coverImage_m)

                        for (post: CustomMarkerResponse in response.body()!!) {

                            var location = LatLng(post.latitude, post.longitude)



                            Picasso.get().load(UtilityFunctions.getFullImagePath(post.coverImage))
                                .into(object : com.squareup.picasso.Target {
                                    override fun onBitmapLoaded(
                                        bitmap: Bitmap?,
                                        from: Picasso.LoadedFrom?
                                    ) {

                                        var marker = map.addMarker(
                                            MarkerOptions().position(location)
                                                .icon(
                                                    BitmapDescriptorFactory.fromBitmap(
                                                        getMarkerBitmapFromView(
                                                            mCustomMarkerView,
                                                            bitmap
                                                        )!!
                                                    )
                                                )
                                        )

                                        marker?.tag = post.postId

                                    }

                                    override fun onBitmapFailed(
                                        e: Exception?,
                                        errorDrawable: Drawable?
                                    ) {

                                    }

                                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                    }

                                })

                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {

                Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
            }

        })

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

                        followers_count_m.text = response.body()!!.followingCount.toString()
                        following_count_m.text = response.body()!!.followersCount.toString()


                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(picture)).into(shapeableImageView_m)
                        }


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong!", Toast.LENGTH_LONG).show()
                }

            })
    }


    private fun setupGetFollowers() {

        ll_followers_um.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = false
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }

    private fun setupGetFollowing() {

        ll_following_um.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = true
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);


        }

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

}