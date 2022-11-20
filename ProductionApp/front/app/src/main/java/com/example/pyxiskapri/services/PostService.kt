package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.dtos.response.PostResponse
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @POST("api/Post/NewPost")
    fun addPost(@Body requestBody: NewPostRequest) : Call<MessageResponse>

    @GET("api/Post/GetAllPosts")
    fun getAllPosts() : Call<ArrayList<PostResponse>>

    @GET("api/Post/GetUserPosts/{username}")
    fun getUserPosts(@Path(value = "username") username: String) : Call<ArrayList<PostResponse>>

    @GET("api/Post/GetPostById/{id}")
    fun getPostById(@Path(value= "id") id: Int) : Call<PostAdditionalData>

    @PUT("api/Post/SetLike/{postId}")
    fun setLike(@Path(value= "postId") postId: Int) : Call<MessageResponse>

    @DELETE("api/Post/RemoveLike/{postId}")
    fun removeLike(@Path(value= "postId") postId: Int) : Call<MessageResponse>

    @DELETE("api/Post/DeleteUserPost/{postId}")
    fun removePost(@Path(value= "postId") postId: Int) : Call<MessageResponse>

    @GET("api/Post/GetFollowingPosts")
    fun getFollowingPosts() : Call<ArrayList<PostResponse>>



}