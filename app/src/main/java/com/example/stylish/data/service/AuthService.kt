package com.example.stylish.data.service

import com.example.stylish.data.model.LoginRequest
import com.example.stylish.data.model.LoginResposnes
import com.example.stylish.data.model.LogoutResposne
import com.example.stylish.data.model.OtpRequest
import com.example.stylish.data.model.OtpResponse
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.data.model.RegisterResposnes
import com.example.stylish.data.model.SendPassword
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResposnes>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResposnes>

    @GET("logout")
    suspend fun logout(): Response<LogoutResposne>

    @POST("send-otp")
    suspend fun sendOtp(
        @Body request: SendPassword
    ): Response<OtpResponse>

    @POST("reset-password")
    suspend fun resetPassword(
        @Body request: OtpRequest
    ): Response<OtpResponse>
}