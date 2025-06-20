package com.example.stylish.data.repository

import android.util.Log
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.data.model.RegisterResposnes
import com.example.stylish.data.service.AuthService
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authService: AuthService) {
    suspend fun register(request: RegisterRequest): Result<RegisterResposnes> {
        return try {
            val response = authService.register(request)
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
        }
        catch (e:IOException){
            Result.failure(Exception("Tidak ada Internet"))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseErrorMessage(errorBody : String?) : String {
        return try
        {
            val jsonObject = JSONObject(errorBody ?: "{}")
            val errors = jsonObject.optJSONObject("errors")
            errors?.keys()?.asSequence()?.map { key ->
                val errorMessage = errors.getJSONArray(key).join(", ")

                when(key) {
                    "email" -> "Email tidak ditemukan"
                    "password" -> "Password tidak cocok atau salah"
                    else -> errorMessage
                }

            }?.joinToString("\n") ?: "Unknown error"
        }catch (e:Exception){
            "Error Parsing response"
        }
    }
}