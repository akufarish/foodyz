package com.example.stylish.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int,
    val name: String,
    val price: String,
    val merchants: Merchant
): Parcelable

data class CreateMenuRequest(
    val name: String,
    val price: String,
)

data class CreateMenuResposnes(
    val data: Menu
)

data class MenuResponses(
    val data: List<Menu>
)
