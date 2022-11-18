package com.example.pyxiskapri.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.dtos.request.*
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("api/Auth/login")
    fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/register")
    fun register(@Body requestBody: RegisterRequest) : Call<MessageResponse>

    @PUT("api/User/UpdateUser")
    fun editUser(@Body requestBody: EditUserRequest) : Call<LoginResponse>

    @GET("api/User/GetUser")
    fun getUser() : Call<GetUserResponse>

    @PUT("api/User/ChangePassword")
    fun changePassword(@Body requestBody: ChangePasswordRequest) : Call<MessageResponse>

    @GET("api/User/GetUserByUsername/{username}")
    fun getForeignUser(@Path(value = "username") username: String) : Call<GetUserResponse>

    @POST("api/User/AddFollow")
    fun follow(@Body requestBody: AddFollowRequest): Call<MessageResponse>

    @DELETE("api/User/RemoveFollow/{followingUsername}")
    fun unfollow(@Path(value = "followingUsername") followingUsername: String): Call<MessageResponse>

    @POST("api/User/getFollow")
    fun getFollow(@Body requestBody: AddFollowRequest): Call<MessageResponse>




}