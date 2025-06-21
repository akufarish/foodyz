package com.example.stylish.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.stylish.utils.di.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val sharedPrefHelper: SharedPrefHelper): ViewModel() {
    fun getLokasi(): String? {
        Log.d("lokasi", sharedPrefHelper.getLocation().toString())
        return sharedPrefHelper.getLocation()
    }

    fun getKota(): String? {
        Log.d("lokasi", sharedPrefHelper.getKota().toString())
        return sharedPrefHelper.getKota()
    }

    fun setKota(kota: String) {
        sharedPrefHelper.setKota(kota)
    }

    fun setLokasi(lokasi:String) {
        sharedPrefHelper.setLocation(lokasi)
    }
}