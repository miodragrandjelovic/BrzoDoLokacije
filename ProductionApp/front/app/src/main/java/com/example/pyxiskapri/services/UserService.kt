package com.example.pyxiskapri.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.dtos.request.RegisterRequest
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("api/Auth/login")
    fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/register")
    fun register(@Body requestBody: RegisterRequest) : Call<MessageResponse>
}