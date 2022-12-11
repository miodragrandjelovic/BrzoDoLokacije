package com.example.pyxiskapri.activities

import android.animation.ObjectAnimator
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
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.marginTop
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.LocationListAdapter
import com.example.pyxiskapri.dtos.request.MapSearchRequest
import com.example.pyxiskapri.dtos.response.CustomMarkerResponse
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.MultiButtonSelector
import com.example.pyxiskapri.models.MarkerModel
import com.example.pyxiskapri.utility.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_main.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map.navMenuView
import kotlinx.android.synthetic.main.dialog_location_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    private lateinit var multiButtonFragment: MultiButtonSelector

    private lateinit var fromPostLocation: MarkerModel

    private lateinit var markerImage: ImageView
    var mapFlag=0


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

        if(ActivityTransferStorage.flag)
            fromPostLocation = ActivityTransferStorage.openPostToMap

        navMenuView.setIndicator(Constants.NavIndicators.DISCOVER)
    }

    private fun setupMapAndAutocomplete(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        setMapTypeChangeButton()

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

        geocoder = Geocoder(this, Locale.getDefault())

        mCustomMarkerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.view_custom_marker, null)
        markerImage = mCustomMarkerView.findViewById(R.id.marker_image)
        mMarkerImageView = mCustomMarkerView.findViewById(R.id.cover_image)


        if(ActivityTransferStorage.flag)
            setupFromUserPostMap(true)
        setupPostSearching()

        cameraListener()

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

    private fun cameraListener() {
        map.setOnCameraMoveListener {
            val cameraPosition = map.cameraPosition

            if(ActivityTransferStorage.flag)
            {
                if (cameraPosition.zoom > 9.0 && mapFlag==0) {

                    var layoutParams = ConstraintLayout.LayoutParams(248, 340)
                    markerImage.layoutParams = layoutParams

                    layoutParams = ConstraintLayout.LayoutParams(248, 248)
                    mMarkerImageView.layoutParams = layoutParams


                    map.clear()

                    setupFromUserPostMap(false)


                    mapFlag=1
                }
                else if(cameraPosition.zoom < 9.0 && mapFlag==1)
                {
                    var layoutParams = ConstraintLayout.LayoutParams(138, 193)
                    markerImage.layoutParams = layoutParams

                    layoutParams = ConstraintLayout.LayoutParams(138, 138)
                    mMarkerImageView.layoutParams = layoutParams


                    map.clear()

                    setupFromUserPostMap(false)


                    mapFlag=0
                }
            }



        }
    }

    private fun setupFromUserPostMap(pom: Boolean){


        var marker:Marker?

        Picasso.get().load(UtilityFunctions.getFullImagePath(fromPostLocation.coverImage))
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(
                    bitmap: Bitmap?,
                    from: Picasso.LoadedFrom?
                ) {

                    marker=map.addMarker(
                        MarkerOptions().position(LatLng(fromPostLocation.latitude,fromPostLocation.longitude))
                            .icon(
                                BitmapDescriptorFactory.fromBitmap(
                                    getMarkerBitmapFromView(
                                        mCustomMarkerView,
                                        bitmap
                                    )!!
                                )
                            )
                    )

                    if(pom)
                        map.moveCamera(CameraUpdateFactory.newLatLng(marker!!.position))

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
        val context = this

        locationListAdapter = LocationListAdapter(arrayListOf(), ::requestPostsFromSearch)
        rv_locations.adapter = locationListAdapter

        et_search.setOnClickListener{ it.visibility = View.VISIBLE }
        map.setOnMapClickListener { rv_locations.visibility = View.GONE }

        // ON ENTER IN INPUT PRESS
        et_search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    requestLocationsByText()
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


    private fun requestPostsFromSearch(locationId: Int, locationName: String){
        var searchRequest = MapSearchRequest(
            search = locationName,
            sortType =  Constants.SearchType.LOCATION.ordinal,
            countOfResults = multiButtonFragment.value,
            friendsOnly = switch_friendsOnly.isActivated,
            name = "",
            searchType = 0,
            0.0,
            0.0,
            0.0
        )

        apiClient.getPostService(this).getPostsBySearch(searchRequest)
            .enqueue(object : Callback<ArrayList<CustomMarkerResponse>> {
                override fun onResponse(call: Call<ArrayList<CustomMarkerResponse>>, response: Response<ArrayList<CustomMarkerResponse>>) {
                    if(response.isSuccessful)
                        if(response.body() != null && response.body()?.size != 0)
                            setPostMarkers(response.body()!!)
                }
                override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
                }
            })
    }

    lateinit var mCustomMarkerView:View
    lateinit var mMarkerImageView: ImageView

    private fun setPostMarkers(postMarkers: ArrayList<CustomMarkerResponse>) {
        map.clear()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        for (postMarker in postMarkers) {
            Picasso.get().load(UtilityFunctions.getFullImagePath(postMarker.coverImage)).into(
                object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                        var marker = map.addMarker(
                            MarkerOptions().position(LatLng(postMarker.latitude, postMarker.longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView, bitmap)!!))
                        )
                        marker?.tag = postMarker.postId

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


}