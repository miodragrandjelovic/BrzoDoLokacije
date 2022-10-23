package com.example.pyxiskapri

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    suspend fun postLogin(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/registration")
    suspend fun postReg(registerDTO:RegisterDTO) : Response<ResponseBody>

}