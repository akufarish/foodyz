package com.example.stylish.data.model

data class Menu(
    val id: Int
)

data class CreateMenuRequest(
    val name: String,
    val price: String,
)

data class CreateMenuResposnes(
    val data: Menu
)
