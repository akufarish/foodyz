package com.example.stylish.data.repository

import android.util.Log
import com.example.stylish.data.model.Order
import com.example.stylish.data.model.OrderRequest
import com.example.stylish.data.service.OrderService
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderService: OrderService) {
    suspend fun getCurrentOrder(): Result<Order> {
        return try {
            val response = orderService.getCurrentOrder()
            Log.d("response_body", response.errorBody().toString())
            Log.d("response_body", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Tidak ada Internet"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun createOrderUser(request: OrderRequest): Result<Order> {
        return try {
            val response = orderService.storeOrderUser(request)
            Log.d("response_body", response.errorBody().toString())
            Log.d("response_body", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Tidak ada Internet"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAuthUserOrder(): Result<List<Order>> {
        return try {
            val response = orderService.getAuthUserOrder()
            Log.d("response_body", response.errorBody().toString())
            Log.d("response_body", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Tidak ada Internet"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = JSONObject(errorBody ?: "{}")
            val errors = jsonObject.optJSONObject("errors")
            errors?.keys()?.asSequence()?.map { key ->
                val errorMessage = errors.getJSONArray(key).join(", ")

                when (key) {
                    "email" -> "Email tidak ditemukan"
                    "password" -> "Password tidak cocok atau salah"
                    else -> errorMessage
                }

            }?.joinToString("\n") ?: "Unknown error"
        } catch (e: Exception) {
            "Error Parsing response"
        }
    }
}