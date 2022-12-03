package com.example.pyxiskapri.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.R
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android. libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder


    private lateinit var location: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupMapAndAutocomplete()

        setupNavButtons()
    }

    override fun onStop() {
        super.onStop()
        finish()
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
        // Map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (!Places.isInitialized())
            Places.initialize(applicationContext, R.string.API_KEY.toString())

        val placesClient = Places.createClient(this)

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

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        geocoder = Geocoder(this, Locale.getDefault())

        if(this::location.isInitialized) {
            setupForUserPostMap()
        }
        else{

        }

    }




    private fun setupForUserPostMap(){
        location = ActivityTransferStorage.openPostToMap
        map.addMarker(MarkerOptions().position(location))
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }


}