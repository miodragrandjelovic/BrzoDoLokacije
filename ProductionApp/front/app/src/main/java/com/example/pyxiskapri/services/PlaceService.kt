package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.response.*
import retrofit2.Call
import retrofit2.http.*

interface PlaceService {

    @POST("api/Place/FilterLocations")
    fun filterLocations(@Body searchText: String): Call<ArrayList<LocationResponse>>

}