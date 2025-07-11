package com.example.stylish.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.LoginRequest
import com.example.stylish.data.model.LoginResposnes
import com.example.stylish.data.model.LogoutResposne
import com.example.stylish.data.model.OtpRequest
import com.example.stylish.data.model.OtpResponse
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.data.model.RegisterResposnes
import com.example.stylish.data.model.SendPassword
import com.example.stylish.data.repository.AuthRepository
import com.example.stylish.utils.di.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository, private val sharedPrefHelper: SharedPrefHelper) : ViewModel() {
    fun register(email: String, password: String, name: String, phone: String, is_merchant: Boolean, is_driver: Boolean, onSuccess: (RegisterResposnes) -> Unit, onError: (String) -> Unit) {
        val payload: RegisterRequest = RegisterRequest(
            email,
            name,
            password,
            phone,
            is_merchant,
            is_driver
        )
        viewModelScope.launch {
        val result = authRepository.register(payload)

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun login(email: String, password: String, onSuccess: (LoginResposnes) -> Unit, onError: (String) -> Unit) {
        val payload: LoginRequest = LoginRequest(
            email,
            password
        )
        viewModelScope.launch {
            val result = authRepository.login(payload)

            if (result.isSuccess) {
                val token = result.getOrNull()?.token
                val role = result.getOrNull()?.role
                sharedPrefHelper.saveRole(role.toString())
                sharedPrefHelper.saveToken(token.toString())
                onSuccess(result.getOrNull()!!)
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun logout(onSuccess: (LogoutResposne) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.logout()

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                sharedPrefHelper.removeToken()
                Log.d("RegisterViewModel", "Logout success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun removeToken() {
        sharedPrefHelper.removeToken()
    }

    fun checkToken(): String? {
        return sharedPrefHelper.getToken()
    }

    fun getRole() : String? {
        return sharedPrefHelper.getRole()
    }

    fun getCurrentState(): String? {
        return sharedPrefHelper.getCurrentState()
    }

    fun setCurrentState(state: String) {
        return sharedPrefHelper.setCurrentState(state)
    }

    fun sendOtp(payload: SendPassword, onSuccess: (OtpResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.sendOtp(payload)

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun resetPassword(payload: SendPassword, onSuccess: (OtpResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.sendOtp(payload)

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }
}