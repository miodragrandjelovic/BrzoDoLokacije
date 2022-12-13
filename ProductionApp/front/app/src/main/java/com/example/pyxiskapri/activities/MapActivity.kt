package com.example.pyxiskapri.activities

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.marginTop
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.LocationListAdapter
import com.example.pyxiskapri.adapters.PostsOnSameLocationAdapter
import com.example.pyxiskapri.dtos.request.MapSearchRequest
import com.example.pyxiskapri.dtos.response.CustomMarkerResponse
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.MultiButtonSelector
import com.example.pyxiskapri.utility.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.dialog_finish_marker_search.*
import kotlinx.android.synthetic.main.dialog_post_on_same_location.*
import kotlinx.android.synthetic.main.view_custom_marker.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnMapClickListener {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    private lateinit var multiButtonFragment: MultiButtonSelector

    private lateinit var markerImage: ImageView
    var mapFlag=0

    private var markersList: ArrayList<ArrayList<CustomMarkerResponse>> = arrayListOf()

    private lateinit var searchLocation: LatLng

    public var searchDistance: Double = 100.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupMapAndAutocomplete()

        // OPTIONS
        multiButtonFragment = (supportFragmentManager.findFragmentById(R.id.fragment_multibuttonSelector) as MultiButtonSelector)
        setupPrioritizedSelection()
        handleOptionsClick()


        switch_friendsOnly.setOnCheckedChangeListener { _, isChecked ->
            searchFriends = isChecked
            Log.d("SEARCH FRIENDS", searchFriends.toString())
        }

        switch_searchTags.setOnCheckedChangeListener { _, isChecked ->
            searchTags = isChecked
            Log.d("SEARCH TAGS", searchTags.toString())
        }

        navMenuView.setIndicator(Constants.NavIndicators.DISCOVER)
    }






    private fun setupMapAndAutocomplete(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private var searchFriends: Boolean = false
    private var searchTags: Boolean = false



    private fun setCameraForNewMarkers(animateCamera: Boolean = false){
        val latitude = markersList[0][0].latitude
        val longitude = markersList[0][0].longitude

        if(animateCamera)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 8f))
        else
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 8f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        setMapTypeChangeButton()

        map.setOnMarkerClickListener{marker ->
            var groupId = marker.tag as? Int

            if(groupId != null) {
                if (markersList[groupId].size == 1)
                    openSinglePost(markersList[groupId][0].postId)
                else
                    showAllPosts(markersList[groupId])
            }

            true
        }

        map.setOnMapClickListener {
            rv_locations.visibility = View.GONE
        }

        map.setOnMapLongClickListener {
            rv_locations.visibility = View.GONE
            map.clear()
            setPostMarkers()
            map.addMarker(MarkerOptions().position(it))
            tv_latitude.text = StringBuilder().append("Lat: ").append(String.format("%.8f", it.latitude))
            tv_longitude.text = StringBuilder().append("Long: ").append(String.format("%.8f", it.longitude))
            searchLocation = it
        }

        geocoder = Geocoder(this, Locale.getDefault())

        mCustomMarkerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.view_custom_marker, null)
        markerImage = mCustomMarkerView.findViewById(R.id.marker_image)
        mMarkerImageView = mCustomMarkerView.findViewById(R.id.cover_image)


        if(ActivityTransferStorage.openPostToMapSet) {
            markersList = arrayListOf(arrayListOf(ActivityTransferStorage.openPostToMap))
            setPostMarkers()
            ActivityTransferStorage.openPostToMapSet = false
            setCameraForNewMarkers()
        }

        setupPostSearching()

        cameraListener()

        btn_searchMarker.setOnClickListener{
            val dialog = Dialog(this)

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = 800
            layoutParams.height = 500

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_finish_marker_search)
            dialog.window?.attributes = layoutParams

            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            dialog.show()

            dialog.btn_sendSearchRequest.setOnClickListener{
                requestPostsFromCoordinates(searchLocation, targetDistance = dialog.et_distance.text.toString().toDouble())
                dialog.dismiss()
            }


        }

        oldMapZoom = map.cameraPosition.zoom
    }


    private fun openSinglePost(postId: Int){
        val context: Context = this
        apiClient.getPostService(context).GetOnePostById(postId).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val intent = Intent(context, OpenPostActivity::class.java)
                ActivityTransferStorage.postItemToOpenPost = response.body()!!
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private var pslAdapter: PostsOnSameLocationAdapter = PostsOnSameLocationAdapter(arrayListOf(), this)

    private fun showAllPosts(posts: ArrayList<CustomMarkerResponse>){
        val dialog = Dialog(this)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = 1000
        layoutParams.height = 1400

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_post_on_same_location)
        dialog.window?.attributes = layoutParams

        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.gv_postsOnSameLocation.adapter = pslAdapter
        pslAdapter.setPostList(posts)

        dialog.show()
    }














    private var satelliteTypeMap: Boolean = true
    private fun setMapTypeChangeButton(){
        btn_mapType.setOnClickListener {
            if (satelliteTypeMap) {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                tv_mapType.text = "Map"
            }
            else {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                tv_mapType.text = "Satellite"
            }
            satelliteTypeMap = !satelliteTypeMap
        }
    }

    private var oldMapZoom: Float = 10.0f
    private fun cameraListener() {
        map.setOnCameraMoveListener {
            val cameraPosition = map.cameraPosition

            if (cameraPosition.zoom > 9.0 && oldMapZoom < 9.0f) {

                var layoutParams = ConstraintLayout.LayoutParams(248, 340)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(248, 248)
                mMarkerImageView.layoutParams = layoutParams

                map.clear()

                setPostMarkers()
            }
            else if(cameraPosition.zoom < 9.0 && oldMapZoom >= 9.0f)
            {
                var layoutParams = ConstraintLayout.LayoutParams(138, 193)
                markerImage.layoutParams = layoutParams

                layoutParams = ConstraintLayout.LayoutParams(138, 138)
                mMarkerImageView.layoutParams = layoutParams

                map.clear()

                setPostMarkers()
            }

            oldMapZoom = cameraPosition.zoom

        }
    }


    // PRIORITIZE BUTTONS
    private lateinit var previousPrioritizeButton: LinearLayout

    private var prioritizeIndex: Int = 0

    private fun changePrioritizeButton(prioritizeButton: LinearLayout, selectedIndex: Int){
        prioritizeIndex = selectedIndex
        previousPrioritizeButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#252529"))
        prioritizeButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
        previousPrioritizeButton = prioritizeButton
    }

    private fun setupPrioritizedSelection(){
        previousPrioritizeButton = btn_prioritizePopular

        btn_prioritizePopular.setOnClickListener{
            changePrioritizeButton(btn_prioritizePopular, 0)
        }
        btn_prioritizeNewest.setOnClickListener{
            changePrioritizeButton(btn_prioritizeNewest, 1)
        }
        btn_prioritizeDiscussed.setOnClickListener{
            changePrioritizeButton(btn_prioritizeDiscussed, 2)
        }
    }




    // SHOW/HIDE OPTIONS
    private var optionsOpen: Boolean = false
    private fun handleOptionsClick(){
        cl_options.viewTreeObserver.addOnGlobalLayoutListener {
            cl_options.translationY = -cl_options.height.toFloat() - cl_options.marginTop.toFloat() + iv_card.height
        }

        iv_card.setOnClickListener {
            if(!optionsOpen)
                openOptions()
            else
                hideOptions()
        }
    }

    private fun openOptions(){
        ObjectAnimator.ofFloat(cl_options, "translationY", 0f).apply {
            duration = 250
            start()
        }
        ObjectAnimator.ofFloat(iv_optionsOpenIndicator, "rotation", 180f).apply {
            duration = 250
            start()
        }.doOnEnd { optionsOpen = true }
    }

    private fun hideOptions(){
        ObjectAnimator.ofFloat(cl_options, "translationY", - cv_options.height.toFloat() - cl_options.marginTop ).apply {
            duration = 250
            start()
        }
        ObjectAnimator.ofFloat(iv_optionsOpenIndicator, "rotation", 0f).apply {
            duration = 250
            start()
        }.doOnEnd { optionsOpen = false }
    }



    // SEARCH
    private lateinit var locationListAdapter: LocationListAdapter

    private fun setupPostSearching(){
        locationListAdapter = LocationListAdapter(arrayListOf(), ::requestPostsFromSearch)
        rv_locations.adapter = locationListAdapter

        et_search.setOnClickListener{ it.visibility = View.VISIBLE }

        // ON ENTER IN INPUT PRESS
        et_search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(!searchTags)
                        requestLocationsByText()
                    else {
                        requestPostsFromSearch(0, et_search.text.toString().trim(), 2)
                        et_search.text.clear()
                    }

                    return true
                }
                return false
            }
        })
    }


    private val LOCATION_LIST_RESULT_COUNT = 8

    private fun requestLocationsByText(){
        if(et_search.text.toString().trim() == "")
            return

        apiClient.getPlaceService(this).filterLocations(et_search.text.toString().trim())
            .enqueue(object : Callback<ArrayList<LocationResponse>> {
                override fun onResponse(call: Call<ArrayList<LocationResponse>>, response: Response<ArrayList<LocationResponse>>) {
                    if(response.isSuccessful)
                        if(response.body() != null)
                            if(response.body()!!.size >= LOCATION_LIST_RESULT_COUNT)
                                locationListAdapter.setLocations(response.body()!!.take(LOCATION_LIST_RESULT_COUNT) as ArrayList<LocationResponse>)
                            else
                                locationListAdapter.setLocations(response.body()!!)
                }
                override fun onFailure(call: Call<ArrayList<LocationResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
                }
            }
        )

    }

    private fun requestPostsFromCoordinates(searchLocation: LatLng, targetDistance: Double){
        var searchRequest = MapSearchRequest(
            search = "",
            sortType =  Constants.SearchType.LOCATION.ordinal,
            countOfResults = multiButtonFragment.value,
            friendsOnly = searchFriends,
            name = "",
            searchType = 1,
            searchLocation.longitude,
            searchLocation.latitude,
            targetDistance
        )

        var context = this

        apiClient.getPostService(this).getPostsBySearch(searchRequest)
            .enqueue(object : Callback<ArrayList<CustomMarkerResponse>> {
                override fun onResponse(call: Call<ArrayList<CustomMarkerResponse>>, response: Response<ArrayList<CustomMarkerResponse>>) {
                    if(response.isSuccessful)
                        if(response.body() != null && response.body()?.size != 0) {
                            markersList = listToGroupedList(response.body()!!)
                            setCameraForNewMarkers(animateCamera = true)
                            setPostMarkers()
                        }
                        else
                            Toast.makeText(context, "No posts found!", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
                }
            })
    }

    private fun requestPostsFromSearch(locationId: Int, searchText: String, searchTypeT: Int = 0){
        var searchRequest = MapSearchRequest(
            search = searchText,
            sortType =  Constants.SearchType.LOCATION.ordinal,
            countOfResults = multiButtonFragment.value,
            friendsOnly = searchFriends,
            name = "",
            searchType = searchTypeT,
            0.0,
            0.0,
            0.0
        )

        var context = this

        apiClient.getPostService(this).getPostsBySearch(searchRequest)
            .enqueue(object : Callback<ArrayList<CustomMarkerResponse>> {
                override fun onResponse(call: Call<ArrayList<CustomMarkerResponse>>, response: Response<ArrayList<CustomMarkerResponse>>) {
                    if(response.isSuccessful)
                        if(response.body() != null && response.body()?.size != 0) {
                            markersList = listToGroupedList(response.body()!!)
                            setCameraForNewMarkers(animateCamera = true)
                            setPostMarkers()
                        }
                        else
                            Toast.makeText(context, "No posts found!", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
                }
            })
    }

    private fun listToGroupedList(markerList: ArrayList<CustomMarkerResponse>): ArrayList<ArrayList<CustomMarkerResponse>>{
        var groupedList = markerList.groupBy { it.latitude to it.longitude }
        var returnList: ArrayList<ArrayList<CustomMarkerResponse>> = arrayListOf()
        for(group in groupedList)
            returnList.add(group.component2() as ArrayList<CustomMarkerResponse>)
        return returnList
    }


    lateinit var mCustomMarkerView:View
    lateinit var mMarkerImageView: ImageView

    private fun setPostMarkers() {
        map.clear()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        var groupId = 0
        for (group in markersList) {

            Picasso.get().load(UtilityFunctions.getFullImagePath(group[0].coverImage)).into(
                object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                        var marker = map.addMarker(
                            MarkerOptions().position(LatLng(group[0].latitude, group[0].longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView, bitmap, group.size)!!))
                        )
                        marker?.tag = groupId++

                    }
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?)
                    {
                        Log.d("BITMAP FAIL", "BITMAP FAIL")
                    }
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                }
            )




        }
    }


    private fun getMarkerBitmapFromView(view: View, bitmap: Bitmap?, postCount: Int): Bitmap? {

        var textView = view.findViewById<TextView>(R.id.tv_postCount)

        if(postCount == 1){
            textView.visibility = View.INVISIBLE
            view.cover_image.alpha = 1f
        }
        else{
            textView.visibility = View.VISIBLE
            view.cover_image.alpha = 0.5f
            textView.text = StringBuilder().append(postCount).append("+")
        }

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

    override fun onMapClick(p0: LatLng) {
        TODO("Not yet implemented")
    }


}