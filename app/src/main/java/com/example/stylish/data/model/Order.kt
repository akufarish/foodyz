package com.example.stylish.data.model

data class Order(
    val id: Long,
    val dirver_id: Any?,
    val user_id: Long,
    val merchant_id: Long,
    val menu_id: Any?,
    val total: Any?,
    val status: String,
    val location: String,
    val created_at: String,
    val updated_at: String,
    val is_done: Boolean,
    val users: Users,
    val menus: Any?,
    val drivers: Any?,
    val merchant: Merchant,
    val detail_order: List<DetailOrder>,
)

data class Users(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val emailVerifiedAt: Any?,
    val createdAt: String,
    val updatedAt: String,
)

data class DetailOrder(
    val id: Long,
    val order_id: Long,
    val user_id: Long,
    val merchant_id: Long,
    val menu_id: Long,
    val driver_id: Any?,
    val createdAt: String,
    val updatedAt: String,
    val stok: Int,
    val merchant: Merchant,
    val menu: Menu,
)

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