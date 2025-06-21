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
    val password: String,
)

data class RegisterResposnes(
    val status: String,
    val message: String,
)

data class LoginResposnes(
    val status: String,
    val message: String,
    val token: String
)

data class LogoutResposne(
    val status: String,
    val message: String,
    val errors: String,
    val content: String
)
