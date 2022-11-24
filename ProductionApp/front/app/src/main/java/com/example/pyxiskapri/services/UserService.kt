package com.example.pyxiskapri.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.dtos.request.*
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("api/Auth/login")
    fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/register")
    fun register(@Body requestBody: RegisterRequest) : Call<MessageResponse>


//    "FolderPath", "TEST"
//    "FileName", "TEST"
//    "Username", this.et_username.text.toString()
//    "Password", dialog.et_modul_password.text.toString()
//    "FirstName", this.et_first_name.text.toString()
//    "LastName", this.et_last_name.text.toString()
//    "Email", this.et_email.text.toString()
//    "ProfileImage", imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())

    @Multipart
    @PUT("api/User/UpdateUser")
    fun editUser(
        @Part("FolderPath") FolderPath: RequestBody,
        @Part("FileName") fileName: RequestBody,
        @Part("Username") username: RequestBody,
        @Part("Password") password: RequestBody,
        @Part("FirstName") firstName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("Email") email: RequestBody,
        @Part profileImage: MultipartBody.Part
    ) : Call<LoginResponse>

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




}