package com.example.stylish.data.service

import com.example.stylish.data.model.LoginRequest
import com.example.stylish.data.model.LoginResposnes
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.data.model.RegisterResposnes
import retrofit2.Response
import retrofit2.http.Body
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
}