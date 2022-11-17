package com.example.pyxiskapri.utility

import android.content.Context
import com.example.pyxiskapri.services.CommentService
import com.example.pyxiskapri.services.PostService
import com.example.pyxiskapri.services.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var userService: UserService
    private lateinit var postService: PostService
    private lateinit var commentService: CommentService

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    fun getUserService(context: Context): UserService {
        if(!::userService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build()

            userService = retrofit.create(UserService::class.java)
        }
        return  userService
    }

    fun getPostService(context: Context): PostService {
        if(!::postService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build()

            postService = retrofit.create(PostService::class.java)
        }
        return postService
    }

    fun getCommentService(context: Context): CommentService {
        if(!::commentService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build()

            commentService = retrofit.create(CommentService::class.java)
        }
        return commentService
    }
}
