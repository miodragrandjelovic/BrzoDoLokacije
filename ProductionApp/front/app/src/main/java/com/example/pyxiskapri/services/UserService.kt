package com.example.pyxiskapri.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.request.RegisterRequest
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("api/Auth/login")
    fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/register")
    fun register(@Body requestBody: RegisterRequest) : Call<MessageResponse>

    @PUT("api/User/UpdateUser")
    fun editUser(@Body requestBody: EditUserRequest) : Call<MessageResponse>

    @GET("api/User/GetUser")
    fun getUser() : Call<GetUserResponse>


}