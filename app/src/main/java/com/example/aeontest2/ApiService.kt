package com.example.aeontest2

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {


    @POST("login")
     fun loginUser(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @GET("payments")
     fun getPayments(
        @Header("token") token: String,
        @Header("app-key") appKey: String,
        @Header("v") version: String
    ): Response<List<PaymentItem>>
}
