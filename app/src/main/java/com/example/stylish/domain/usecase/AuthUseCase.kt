package com.example.stylish.domain.usecase

import com.example.stylish.data.repository.AuthRepository
import com.example.stylish.utils.di.SharedPrefHelper
import javax.inject.Inject

class AuthUseCase @Inject  constructor(
    private val authRepository: AuthRepository,
    private val sharedPrefHelper: SharedPrefHelper
) {
    fun getToken(): String? {
        return sharedPrefHelper.getToken()
    }

    fun clearToken() {
        sharedPrefHelper.removeToken()
    }
}