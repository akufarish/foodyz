package com.example.stylish.data.model

data class Auth(
    val id: Int,
)

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
)

data class LoginRequest(
    val email: String,
    val name: String,
    val password: String,
)

data class RegisterResposnes(
    val status: String,
    val message: String,
)

