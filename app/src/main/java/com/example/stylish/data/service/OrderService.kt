package com.example.stylish.data.service

import com.example.stylish.data.model.Order
import com.example.stylish.data.model.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {
    @GET("order/current")
    suspend fun getCurrentOrder(): Response<Order>

    @GET("orders")
    suspend fun getAuthUserOrder(): Response<List<Order>>

    @POST("orders")
    suspend fun storeOrderUser(
        @Body orderRequest: OrderRequest
    ): Response<Order>
}