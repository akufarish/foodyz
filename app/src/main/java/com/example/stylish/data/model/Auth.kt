package com.example.stylish.data.model

data class Auth(
    val id: Int,
)

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
    val is_merchant: Boolean = false,
    val is_driver: Boolean = false
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
    val token: String,
    val role: String,
    val merchant: Merchant?
)

data class LogoutResposne(
    val status: String,
    val message: String,
    val errors: String,
    val content: String
)

data class SendPassword(
    val email: String,
)

data class OtpRequest(
    val email: String,
    val otp: String
)

data class OtpResponse(
    val otp: String,
    val message: String?
)