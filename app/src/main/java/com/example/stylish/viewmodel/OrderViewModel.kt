package com.example.stylish.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.model.DriverGetOrderRequest
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.Order
import com.example.stylish.data.model.OrderRequest
import com.example.stylish.data.model.RegisterResposnes
import com.example.stylish.data.model.UpdateStatusOrder
import com.example.stylish.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository: OrderRepository): ViewModel() {
    private val _order = MutableLiveData<Order>()
    val order: MutableLiveData<Order> get() = _order
    private val _allOrder = MutableLiveData<List<Order>>()
    val allOrder: MutableLiveData<List<Order>> get() = _allOrder

    fun getCurrentOrder(onSuccess: (Order) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.getCurrentOrder()

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _order.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun driverTakeOrder(id: Int, onSuccess: (Order) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.driverTakeOrder(id)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _order.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun updateStatusOrder(id: Int,payload: UpdateStatusOrder,  onSuccess: (Order) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.updateStatusOrder(id, payload)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _order.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }


    fun getAuthUserOrder(onSuccess: (List<Order>) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.getAuthUserOrder()

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _allOrder.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun getDriverHistory(onSuccess: (List<Order>) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.getDriverHistory()

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _allOrder.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }
    fun getMerchantHistory(onSuccess: (List<Order>) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.getMerchantHistory()

            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _allOrder.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun driverGetOrder(payload: DriverGetOrderRequest, onSuccess: (List<Order>) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.driverGetOrder(payload)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                _allOrder.value = result.getOrNull()!!
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun createOrderUser(request: OrderRequest, onSuccess: (Order) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = orderRepository.createOrderUser(request)
            if (result.isSuccess) {
                onSuccess(result.getOrNull()!!)
                Log.d("orderViewModel", "Register success: ${result.getOrNull()}")
            } else {
                onError(result.exceptionOrNull()?.message ?: "Unknown error")
                Log.d("orderViewModel", "Register error ${result.exceptionOrNull()?.message}")
            }
        }
    }


    interface onMenuClickListener {
        fun onDetailClick(order: Order)
    }
}