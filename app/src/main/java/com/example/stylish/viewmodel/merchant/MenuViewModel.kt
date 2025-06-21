package com.example.stylish.viewmodel.merchant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.CreateMenuRequest
import com.example.stylish.data.model.CreateMenuResposnes
import com.example.stylish.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository): ViewModel() {
    fun createMenu(name: String, price: String, onSuccess: (CreateMenuResposnes) -> Unit, onError: (String) -> Unit) {
        val payload: CreateMenuRequest = CreateMenuRequest(
            name, price
        )
        viewModelScope.launch {
            val result = menuRepository.createMenu(payload)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            }
        }
    }
}