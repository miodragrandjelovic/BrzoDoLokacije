package com.example.pyxiskapri.services

import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostAdditionalData
import com.example.pyxiskapri.dtos.response.PostOnMapResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @Multipart
    @POST("api/Post/NewPost")
    fun addPost(
        @Part CoverImage: MultipartBody.Part,
        @Part Images: ArrayList<MultipartBody.Part>,
        @Part("Description") Description: RequestBody,
        @Part("Longitude") Longitude: RequestBody,
        @Part("Latitude") Latitude: RequestBody,
        @Part("LocationName") LocationName: RequestBody,
        @Part("Address") Address: RequestBody,
        @Part("City") City: RequestBody,
        @Part("Country") Country: RequestBody
    ) : Call<MessageResponse>

    @GET("api/Post/GetAllPosts/{sortType}")
    fun getAllPosts(@Path(value = "sortType") sortType: Int) : Call<ArrayList<PostResponse>>

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

    @GET("api/Post/GetFollowingPosts/0")
    fun getFollowingPosts() : Call<ArrayList<PostResponse>>

    @GET("api/Post/GetUsersPostOnMap/{username}")
    fun PostOnMap(@Path(value = "username") username: String) : Call<ArrayList<PostOnMapResponse>>


}