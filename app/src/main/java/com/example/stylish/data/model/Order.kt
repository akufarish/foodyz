package com.example.stylish.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: Long,
    val driver_id: Int?,
    val user_id: Long,
    val merchant_id: Long,
    val menu_id: Int?,
    val total: Int?,
    val status: String,
    val location: String,
    val created_at: String,
    val updated_at: String,
    val is_done: Boolean,
    val is_taken: Boolean,
    val users: Users?,
    val menus: Menu?,
    val drivers: Users?,
    val merchant: Merchant?,
    val detail_order: List<DetailOrder>,
    val latitude: Double?,
    val longtitude: Double?,
    val is_diambil: Boolean?
): Parcelable

@Parcelize
data class Users(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
//    val emailVerifiedAt: Boolean?,
    val createdAt: String?,
    val updatedAt: String?,
): Parcelable

@Parcelize
data class DetailOrder(
    val id: Long,
    val order_id: Long,
    val user_id: Long,
    val merchant_id: Long,
    val menu_id: Long,
    val driver_id: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val stok: Int,
    val merchant: Merchant,
    val menu: Menu,
): Parcelable

data class OrderRequest(
    val merchant_id: Int,
    val location: String,
    val menu: List<MenuOrderRequest>,
    val longtitude: Double,
    val latitude: Double
)

data class MenuOrderRequest(
    val id: Int,
    val stok: Int,
    val catatan: String
)

data class DriverGetOrderRequest(
    val lokasi: String
)

data class UpdateStatusOrder(
    val is_diambil: Boolean?,
    val is_done: Boolean?,
    val _method: String
)