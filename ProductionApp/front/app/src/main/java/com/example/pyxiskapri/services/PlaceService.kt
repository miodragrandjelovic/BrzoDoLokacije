package com.example.pyxiskapri.services

import android.widget.EditText
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface PlaceService {

    @POST("api/Place/FilterLocations")
    fun getLocationsByString(@Body searchText: String) : Call<MutableList<LocationResponse>>

}