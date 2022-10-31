package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.MessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    @POST("api/Post/NewPost")
    fun addPost(@Body requestBody: NewPostRequest) : Call<MessageResponse>

}