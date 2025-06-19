package com.example.stylish.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylish.R
import com.example.stylish.adapter.AuthAdapter
import com.example.stylish.databinding.ActivityAuthBinding
import com.example.stylish.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding?.createAccountButton?.setOnClickListener {
            showBottomSheet()
        }

        binding?.loginButton?.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val sheetDialog = BottomSheetDialog(this@AuthActivity, R.style.CustomBottomSheetDialogTheme)
        val sheetBinding = BottomSheetBinding.inflate(layoutInflater)

        val authAdapter = AuthAdapter(supportFragmentManager, lifecycle)

        sheetBinding.apply {
            sheetBinding.authViewPager.apply {
                adapter = authAdapter
            }
        }

        TabLayoutMediator(
            sheetBinding.authTabLayout,
            sheetBinding.authViewPager
        ) {tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.register)
                1 -> tab.text = getString(R.string.login)
            }
        }.attach()

        sheetDialog.apply {
            setContentView(sheetBinding.root)
            show()
        }
    }
}