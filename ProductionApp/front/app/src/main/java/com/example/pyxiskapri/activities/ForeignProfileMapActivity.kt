package com.example.pyxiskapri.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.AddFollowRequest
import com.example.pyxiskapri.dtos.response.*
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
import kotlinx.android.synthetic.main.activity_foreign_profile_map.*
import kotlinx.android.synthetic.main.activity_map_user_post.*
import kotlinx.android.synthetic.main.modal_confirm_follow.*
import kotlinx.android.synthetic.main.modal_confirm_unfollow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ForeignProfileMapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    lateinit var username:String

    lateinit var mCustomMarkerView:View
    lateinit var mMarkerImageView: ImageView
    lateinit var markerImage: ImageView

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    var averageGrade:Double = 0.0

    var flag = 0

    override fun onRestart() {
        super.onRestart()
        getForeignUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign_profile_map)

        ll_posts_fm.setOnClickListener(){
            val intent = Intent (this, ForeignProfileGridActivity::class.java);
            intent.putExtra("username", username)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent);
        }

        val bundle = intent.extras
        username = bundle?.getString("username")!!
        averageGrade = bundle.getDouble("averageGrade")


        apiClient= ApiClient()
        sessionManager= SessionManager(this)

        getForeignUser()


        ib_follow_fm.setOnClickListener(){
            followProfile()
        }


        ib_following_fm.setOnClickListener(){
            unfollowProfile()
        }

        mCustomMarkerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

        mMarkerImageView = mCustomMarkerView.findViewById(R.id.cover_image)

        markerImage = mCustomMarkerView.findViewById(R.id.marker_image)


        setupMap()

        setupGetFollowers()
        setupGetFollowing()

        setupSetStatistics()

    }

    private fun setupSetStatistics() {

        ll_map_fm.setOnClickListener(){

            tv_statistics_fm.setTextColor(Color.WHITE)
            tv_map_fm.setTextColor(Color.parseColor("#CC2045"))

            sv_statistics_fm.isGone=true
            map_fm.requireView().visibility = View.VISIBLE;


        }

        ll_statistics_fm.setOnClickListener(){


            tv_statistics_fm.setTextColor(Color.parseColor("#CC2045"))
            tv_map_fm.setTextColor(Color.WHITE)

            sv_statistics_fm.isGone=false
            map_fm.requireView().visibility = View.GONE;


            average_grade_fm.text=averageGrade.toString()
            post_number_statistics_fm.text=post_number_fm.text.toString()+ " posts"

            if(averageGrade == 0.00)
                emoji_fm.setImageResource(R.drawable.emoji_unset)
            else if(averageGrade < 1.5)
                emoji_fm.setImageResource(R.drawable.emoji_crying)
            else if(averageGrade < 2.5)
                emoji_fm.setImageResource(R.drawable.emoji_sad)
            else if(averageGrade < 3.5)
                emoji_fm.setImageResource(R.drawable.emoji_neutral)
            else if(averageGrade < 4.5)
                emoji_fm.setImageResource(R.drawable.emoji_happy)
            else if(averageGrade <= 5.0)
                emoji_fm.setImageResource(R.drawable.emoji_amazed)


            apiClient.getPostService(this).getUserTopPosts(username).enqueue(object : Callback<ArrayList<StatisticsResponse>>{
                override fun onResponse(call: Call<ArrayList<StatisticsResponse>>, response: Response<ArrayList<StatisticsResponse>>) {
                    if(response.isSuccessful && response.body() != null){
                        var size = response.body()!!.size

                        if(size>0)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(iv_coverImage_prvi_fm)
                            gradeDisplay_followed_prvi_fm.setupForFollowed()
                            gradeDisplay_followed_prvi_fm.setGradeDisplay(response.body()!![0].averageGrade,response.body()!![0].gradesCount)

                        }
                        else
                        {
                            ll_prvi_grade_fm.isGone=true
                            tv_no_post_prvi_fm.isVisible=true
                        }

                        //drugi
                        if(size>1)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![1].coverImage)).into(iv_coverImage_drugi_fm)
                            gradeDisplay_followed_drugi_fm.setupForFollowed()
                            gradeDisplay_followed_drugi_fm.setGradeDisplay(response.body()!![1].averageGrade,response.body()!![1].gradesCount)
                        }
                        else
                        {
                            ll_drugi_grade_fm.isGone=true
                            tv_no_post_drugi_fm.isVisible=true
                        }

                        //treci

                        if(size>2)
                        {
                            Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![2].coverImage)).into(iv_coverImage_treci_fm)
                            gradeDisplay_followed_treci_fm.setupForFollowed()
                            gradeDisplay_followed_treci_fm.setGradeDisplay(response.body()!![2].averageGrade,response.body()!![2].gradesCount)

                        }
                        else
                        {
                            ll_treci_grade_fm.isGone=true
                            tv_no_post_treci_fm.isVisible=true
                        }
                    }



                }

                override fun onFailure(call: Call<ArrayList<StatisticsResponse>>, t: Throwable) {

                }

            })

        }

    }

    private fun setupMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fm) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))

        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        geocoder = Geocoder(this, Locale.getDefault())
        addCustomMarkerFromURL(true);

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


            if (cameraPosition.zoom > 9.0 && flag==0) {

                var layoutParams = ConstraintLayout.LayoutParams(248, 340)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(248, 248)
                mMarkerImageView.layoutParams = layoutParams


                map.clear()

                addCustomMarkerFromURL(false)


                flag=1
            }
            else if(cameraPosition.zoom < 9.0 && flag==1)
            {
                var layoutParams = ConstraintLayout.LayoutParams(138, 193)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(138, 138)
                mMarkerImageView.layoutParams = layoutParams


                map.clear()

                addCustomMarkerFromURL(false)


                flag=0
            }


        }


    }

    private fun getMarkerBitmapFromView(view: View, bitmap: Bitmap?): Bitmap? {

        mMarkerImageView.setImageBitmap(bitmap)

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

        apiClient.getPostService(context).PostOnMap(username).enqueue(object : Callback<ArrayList<CustomMarkerResponse>>{
            override fun onResponse(
                call: Call<ArrayList<CustomMarkerResponse>>,
                response: Response<ArrayList<CustomMarkerResponse>>
            ) {

                if(pom) {

                    post_number_fm.text = response.body()!!.size.toString()

                    var cameraLocation: LatLng =
                        LatLng(response.body()!![0].latitude, response.body()!![0].longitude)

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraLocation, 3f))

                    Picasso.get().load(UtilityFunctions.getFullImagePath(response.body()!![0].coverImage)).into(coverImage_fm)

                }

                for(post: CustomMarkerResponse in response.body()!!)
                {
                    var location: LatLng = LatLng(post.latitude,post.longitude)


                    Picasso.get().load(UtilityFunctions.getFullImagePath(post.coverImage)).into(object : com.squareup.picasso.Target{
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                            var marker = map.addMarker( MarkerOptions().position(location)
                                .icon( BitmapDescriptorFactory.fromBitmap(
                                    getMarkerBitmapFromView(mCustomMarkerView, bitmap)!!)))

                            marker?.tag=post.postId

                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                        }

                    })


                }

            }

            override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {

                Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
            }

        })


    }


    private fun followProfile() {


        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_follow)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.show()

        dialog.tv_follow.text="Are you sure you want to follow "+username+"?"

        dialog.btn_close_dialog_f.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.btn_confirm_follow.setOnClickListener(){

            val context: Context =this

            val addFollowRequest= AddFollowRequest(
                username = username
            )

            apiClient.getUserService(context).follow(addFollowRequest).enqueue(object :
                Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        ib_follow_fm.isGone=true
                        ib_following_fm.isGone=false
                        tv_follow_ing_fm.text="Following"
                    }
                    else
                    {
                        Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                    }

                    dialog.dismiss()

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                    Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            })

        }


    }


    private fun unfollowProfile() {


        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_unfollow)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.show()

        dialog.tv_unfollow.text="Are you sure you want to unfollow "+username+"?"

        dialog.btn_close_dialog_uf.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.btn_confirm_unfollow.setOnClickListener()
        {

            val context: Context =this
            apiClient.getUserService(context).unfollow(username).enqueue(object :
                Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        ib_follow_fm.isGone=false
                        ib_following_fm.isGone=true
                        tv_follow_ing_fm.text="Follow"
                    }

                    else
                    {
                        Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                    }
                    dialog.dismiss()

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            })

        }


    }

    private fun getForeignUser() {

        val context: Context =this
        apiClient.getUserService(context).getForeignUser(username).enqueue(object :
            Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if(response.isSuccessful)
                {
                    tv_name1_fm.text=response.body()!!.firstName
                    tv_name2_fm.text=response.body()!!.lastName

                    followers_count_fm.text = response.body()!!.followingCount.toString()
                    following_count_fm.text = response.body()!!.followersCount.toString()



                    val picture=response.body()!!.profileImage
                    if(picture!=null)
                    {
                        Picasso.get().load(UtilityFunctions.getFullImagePath(picture)).into(shapeableImageView_fm)
                    }


                    apiClient.getUserService(context).getFollow(username).enqueue(object :
                        Callback<MessageResponse> {
                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {

                            if(response.isSuccessful)
                            {

                                if(response.body()!!.message=="True")
                                {
                                    ib_follow_fm.isGone=true
                                    ib_following_fm.isGone=false
                                    tv_follow_ing_fm.text="Following"
                                }

                                else
                                {

                                    ib_follow_fm.isGone=false
                                    ib_following_fm.isGone=true
                                    tv_follow_ing_fm.text="Follow"
                                }

                            }

                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable)
                        {

                            Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()
                        }

                    })

                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun setupGetFollowers() {

        ll_followers_fm.setOnClickListener(){

            var followList = FollowList(
                username = username,
                type = false
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }

    private fun setupGetFollowing() {

        ll_following_fm.setOnClickListener(){

            var followList = FollowList(
                username = username,
                type = true
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }

}