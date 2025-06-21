package com.example.stylish.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.example.stylish.databinding.ActivitySplashBinding
import com.example.stylish.ui.driver.activity.DriverHomeActivity
import com.example.stylish.ui.home.HomeActivity
import com.example.stylish.ui.merchant.activity.MerchantHomeActivity
import com.example.stylish.ui.onboard.OnBoardActivity
import com.example.stylish.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )



        checkLogin()
    }

    private fun checkLogin() {
        val token = authViewModel.checkToken()

        if (token.isNullOrEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(
                    Intent(this@SplashActivity, OnBoardActivity::class.java)
                )
            }, 2000)
        } else {
           val role = authViewModel.getRole()
            Log.d("role", role.toString());

            when (role) {
                "user" -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(
                            Intent(this@SplashActivity, HomeActivity::class.java)
                        )
                    }, 2000)
                }
                "merchant" -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(
                            Intent(this@SplashActivity, MerchantHomeActivity::class.java)
                        )
                    }, 2000)
                }
                "driver" -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(
                            Intent(this@SplashActivity, DriverHomeActivity::class.java)
                        )
                    }, 2000)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}