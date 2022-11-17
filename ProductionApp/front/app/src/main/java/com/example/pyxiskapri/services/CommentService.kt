package com.example.pyxiskapri.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.dtos.request.*
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.*
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface CommentService {
    @GET("api/Comment/GetCommentsPost{postId}")
    fun getPostComments(@Path(value = "postId") postId: Int): Call<ArrayList<CommentResponse>>

    @POST("api/Comment/AddComment")
    fun addNewComment(@Body requestBody: NewCommentRequest) : Call<MessageResponse>

}