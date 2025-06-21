package com.example.stylish.viewmodel.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.CreateMenuRequest
import com.example.stylish.data.model.CreateMenuResposnes
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.MenuResponses
import com.example.stylish.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository): ViewModel() {
    private val _menu = MutableLiveData<MenuResponses>()
    val menu: MutableLiveData<MenuResponses> get() = _menu

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

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

    fun getMenu(onSuccess: (MenuResponses) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = menuRepository.getMenu()
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _menu.value = result.getOrNull()!!
                Log.d("menuViewModel", "Register success: ${result.getOrNull()}")
                Log.d("menuViewModel", "Register success: ${menu.value}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("RegisterViewModel", "Register success: ${result.getOrNull()}")
            }
        }
    }

    interface onMenuClickListener {
        fun onDetailClick(menu: Menu)
    }
}