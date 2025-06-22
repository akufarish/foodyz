package com.example.stylish.data.service

import com.example.stylish.data.model.DriverGetOrderRequest
import com.example.stylish.data.model.Order
import com.example.stylish.data.model.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("order/current")
    suspend fun getCurrentOrder(): Response<Order>

    @GET("orders")
    suspend fun getAuthUserOrder(): Response<List<Order>>

    @POST("orders")
    suspend fun storeOrderUser(
        @Body orderRequest: OrderRequest
    ): Response<Order>

    @POST("driver/order")
    suspend fun driverGetOrder(
        @Body orderRequest: DriverGetOrderRequest
    ): Response<List<Order>>

    @POST("driver/order/{id}")
    suspend fun driverTakeOrder(
        @Path("id") id: Int
    ): Response<Order>
}