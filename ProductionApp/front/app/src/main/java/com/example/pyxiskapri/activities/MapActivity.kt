package com.example.pyxiskapri.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.LocationListAdapter
import com.example.pyxiskapri.dtos.request.MapSearchRequest
import com.example.pyxiskapri.dtos.response.CustomMarkerResponse
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.dtos.response.PostOnMapResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.fragments.MultiButtonSelector
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map.btn_home
import kotlinx.android.synthetic.main.activity_map.btn_messages
import kotlinx.android.synthetic.main.activity_map.btn_newPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    private lateinit var multiButtonFragment: MultiButtonSelector

    private lateinit var fromPostLocation: LatLng



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupMapAndAutocomplete()

        setupNavButtons()

        // OPTIONS
        multiButtonFragment = (supportFragmentManager.findFragmentById(R.id.fragment_multibuttonSelector) as MultiButtonSelector)
        setupPrioritizedSelection()
        handleOptionsClick()

        setupPostSearching()
    }

    private fun setupNavButtons(){
        // NEW POST
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }

        // HOME
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }

        // MESSAGES
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }

        // NOTIFICATIONS
    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavMap.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupMapAndAutocomplete(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /* GOOGLE MAPS (preko PLACES)
        if (!com.google.android.libraries.places.api.Places.isInitialized())
            com.google.android.libraries.places.api.Places.initialize(applicationContext, R.string.API_KEY.toString())

        val placesClient = com.google.android.libraries.places.api.Places.createClient(this)

        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        //autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.name + ", " + place.id)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        */
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        geocoder = Geocoder(this, Locale.getDefault())

        if(this::fromPostLocation.isInitialized) {
            setupFromUserPostMap()
        }
        else{
            setupNormalMap()
        }

    }

    private fun setupFromUserPostMap(){
        fromPostLocation = ActivityTransferStorage.openPostToMap
        Log.d("TEST", fromPostLocation.toString())
        map.addMarker(MarkerOptions().position(fromPostLocation))
        map.moveCamera(CameraUpdateFactory.newLatLng(fromPostLocation))
    }

    private fun setupNormalMap(){

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
            optionsOpen = !optionsOpen
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
        }
    }

    private fun hideOptions(){
        ObjectAnimator.ofFloat(cl_options, "translationY", - cv_options.height.toFloat() - cl_options.marginTop ).apply {
            duration = 250
            start()
        }
        ObjectAnimator.ofFloat(iv_optionsOpenIndicator, "rotation", 0f).apply {
            duration = 250
            start()
        }
    }






    // SEARCH
    private lateinit var locationListAdapter: LocationListAdapter

    private fun setupPostSearching(){
        val context = this

        locationListAdapter = LocationListAdapter(arrayListOf(), ::requestPostsFromSearch)
        rv_locations.adapter = locationListAdapter

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

        iv_searchIcon.setOnClickListener {
            requestLocationsByText()
        }
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
            switch_friendsOnly.isActivated,
        )

        apiClient.getPostService(this).getPostsBySearch(searchRequest)
            .enqueue(object : Callback<ArrayList<CustomMarkerResponse>> {
                override fun onResponse(call: Call<ArrayList<CustomMarkerResponse>>, response: Response<ArrayList<CustomMarkerResponse>>) {
                    if(response.isSuccessful)
                        Log.d("POSTS: ", response.body().toString())
                }
                override fun onFailure(call: Call<ArrayList<CustomMarkerResponse>>, t: Throwable) {
                    Log.d("TEST", "TEST")
                }
            })
    }



}