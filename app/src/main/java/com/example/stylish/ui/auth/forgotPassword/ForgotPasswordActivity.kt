package com.example.stylish.ui.auth.forgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.stylish.R
import com.example.stylish.data.model.SendPassword
import com.example.stylish.databinding.ActivityForgotPasswordBinding
import com.example.stylish.ui.success.SuccessActivity
import com.example.stylish.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private var _binding: ActivityForgotPasswordBinding? = null
    private val binding get() = _binding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        resetPassword()
        checkCurrentState()
    }

    private fun checkCurrentState() {
        val state = authViewModel.getCurrentState()

        if (state == "reset_password") {
            binding?.welcomeBackLabel?.text = "Change New Password"
            binding?.forgotPassword?.text = "Enter Your New Password"

            resetPass()
        }
    }

    private fun resetPass() {
        
    }

    private fun resetPassword() {
        binding?.loginButton?.setOnClickListener {
            val email = binding?.emailEditText?.text.toString()
            val payload = SendPassword(email)
            authViewModel.sendOtp(payload, onSuccess = {
                response ->
                Log.d("Forgot Password Activity: Success", response.toString())
                startActivity(
                    Intent(this@ForgotPasswordActivity, SuccessActivity::class.java)
                )
                authViewModel.setCurrentState("reset_password")
            }, onError = {
                response ->
                Log.d("Forgot Password Activity: Error", response.toString())
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}