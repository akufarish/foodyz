package com.example.stylish.viewmodel.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.CreateMenuResposnes
import com.example.stylish.data.model.CreateMerchantRequest
import com.example.stylish.data.model.CreateMerchantResposne
import com.example.stylish.data.repository.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MerchantViewModel @Inject constructor(private val merchantRepository: MerchantRepository): ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun createMerchant(payload: CreateMerchantRequest, onSuccess: (CreateMerchantResposne) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = merchantRepository.createMerchant(payload)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("MerchantViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("MerchantViewModel", "Register success: ${result.getOrNull()}")
            }
        }
    }
}