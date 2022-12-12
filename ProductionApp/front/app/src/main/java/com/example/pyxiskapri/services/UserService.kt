package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.request.*
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("api/Auth/login")
    fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/register")
    fun register(@Body requestBody: RegisterRequest) : Call<MessageResponse>

    @PUT("api/User/UpdateUser")
    fun editUserData(@Body requestBody: EditUserRequest) : Call<LoginResponse>

    @Multipart
    @PUT("api/User/UpdateProfileImage")
    fun editUserImage(@Part profileImage: MultipartBody.Part) : Call<LoginResponse>

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

    @GET("api/User/IsFollowed/{followingUsername}")
    fun getFollow(@Path(value = "followingUsername") followingUsername: String): Call<MessageResponse>

    @GET("api/User/GetFollowing/{username}")
    fun getFollowing(@Path(value = "username") username: String): Call<ArrayList<FollowUserResponse>>

    @GET("api/User/GetFollowers/{username}")
    fun getFollowers(@Path(value = "username") username: String): Call<ArrayList<FollowUserResponse>>


    @GET("api/User/SearchFollowing/{search}")
    fun searchUsers(@Path(value = "search") search: String): Call<ArrayList<FollowUserResponse>>


}