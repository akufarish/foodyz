package com.example.stylish.utils

import android.util.Log
import com.example.stylish.utils.di.SharedPrefHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPrefHelper: SharedPrefHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefHelper.getToken()
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
        token?.let{
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }

}