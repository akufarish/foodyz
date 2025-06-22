package com.example.stylish.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Merchant(
    val id: Int,
    val user_id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val status: String
): Parcelable

data class CreateMerchantRequest(
    val name: String,
    val address: String,
    val phone: String,
    val latitude: Double,
    val longtitude: Double,
)

data class CreateMerchantResposne(
    val data: Merchant
)
