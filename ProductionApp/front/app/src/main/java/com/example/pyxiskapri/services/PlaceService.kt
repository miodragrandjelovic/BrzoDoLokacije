package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.request.MapSearchRequest
import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PlaceService {

    @POST("api/Place/FilterLocations")
    fun filterLocations(@Body searchText: String): Call<ArrayList<LocationResponse>>

}