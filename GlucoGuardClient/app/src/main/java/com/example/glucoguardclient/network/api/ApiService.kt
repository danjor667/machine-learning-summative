package com.example.glucoguardclient.network.api

import com.example.glucoguardclient.data.send.LogInUser
import com.example.glucoguardclient.data.response.LoginResponse
import com.example.glucoguardclient.data.send.PostData
import com.example.glucoguardclient.data.response.PredictionResponse
import com.example.glucoguardclient.data.response.RegisterResponse
import com.example.glucoguardclient.data.send.User
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Header

import retrofit2.http.POST

interface ApiService {

    //login
    @POST("/login/")
    suspend fun loginUser(@Body logInUser: LogInUser): Response<LoginResponse>

    //register
    @POST("/register/")
    suspend fun register(
        @Body user: User,
    ): Response<RegisterResponse>

    //get prediction
    @POST("/predict/")
    suspend fun getPrediction(@Header("Authorization") token: String, @Body postData: PostData): Response<PredictionResponse>

}
