package com.example.stylish.data.service

import com.example.stylish.data.model.CreateMenuRequest
import com.example.stylish.data.model.CreateMenuResposnes
import com.example.stylish.data.model.MenuResponses
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface MenuService {
    @POST("menus")
    suspend fun createMenu(
        @Body request: CreateMenuRequest
    ): Response<CreateMenuResposnes>

    @GET("menus")
    suspend fun getMenu(): Response<MenuResponses>

    @GET("rand-menu")
    suspend fun getRandMenu(): Response<MenuResponses>
}