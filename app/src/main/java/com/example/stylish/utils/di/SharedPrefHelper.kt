package com.example.stylish.utils.di

import android.content.Context
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(context : Context)  {
    private val sharedPref = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun saveToken (token : String) {
        sharedPref.edit().putString("token", token).apply()
    }
    fun getToken () : String? {
        return sharedPref.getString("token", null)
    }
    fun removeToken () {
        sharedPref.edit().remove("token").apply()
    }
}