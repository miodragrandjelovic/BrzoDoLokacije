package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostFullResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @POST("api/Post/NewPost")
    fun addPost(@Body requestBody: NewPostRequest) : Call<MessageResponse>

    @GET("api/Post/GetUserPosts/{username}")
    fun getUserPosts(@Path(value = "username") username: String) : Call<ArrayList<PostResponse>>

    @GET("api/Post/GetPostById/{id}")
    fun getPostById(@Path(value= "id") id: Int) : Call<PostFullResponse>

}