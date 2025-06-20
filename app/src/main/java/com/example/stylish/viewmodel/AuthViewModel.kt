package com.example.stylish.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.data.model.RegisterResposnes
import com.example.stylish.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    fun register(email: String, password: String, name: String, phone: String, onSuccess: (RegisterResposnes) -> Unit, onError: (String) -> Unit) {
        val payload: RegisterRequest = RegisterRequest(
            email,
            name,
            password,
            phone
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
}