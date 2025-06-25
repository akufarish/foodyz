package com.example.stylish.ui.success

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylish.R
import com.example.stylish.databinding.ActivitySuccessBinding
import com.example.stylish.ui.auth.forgotPassword.ForgotPasswordActivity
import com.example.stylish.ui.onboard.OnBoardActivity

class SuccessActivity : AppCompatActivity() {
    private var _binding: ActivitySuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this@SuccessActivity, ForgotPasswordActivity::class.java)
            )
        }, 2000)
    }
}