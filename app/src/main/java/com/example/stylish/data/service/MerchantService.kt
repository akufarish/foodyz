package com.example.stylish.data.service

import com.example.stylish.data.model.CreateMerchantRequest
import com.example.stylish.data.model.CreateMerchantResposne
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MerchantService {
    @POST("merchants")
    suspend fun createMerchant(
        @Body request: CreateMerchantRequest
    ): Response<CreateMerchantResposne>
}