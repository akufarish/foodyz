package com.example.stylish.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.databinding.FragmentLoginBinding
import com.example.stylish.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.stylish.ui.driver.activity.DriverHomeActivity
import com.example.stylish.ui.merchant.activity.MakeMerchantActivity
import com.example.stylish.ui.merchant.activity.MerchantHomeActivity
import com.example.stylish.viewmodel.AuthViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        login()
    }

    private fun login() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            authViewModel.login(email, password, onSuccess = {
                response ->
                Log.d("login_role", response.role.toString())
                Log.d("login_role", response.merchant.toString())
                if (response.role == "merchant") {
                    if (response.merchant != null) {
                        startActivity(
                            Intent(requireContext(),  MerchantHomeActivity::class.java)
                        )
                    } else {
                        startActivity(
                            Intent(requireContext(),  MakeMerchantActivity::class.java)
                        )
                    }
                } else if (response.role == "driver") {
                    startActivity(
                        Intent(requireContext(),  DriverHomeActivity::class.java)
                    )
                } else {
                    startActivity(
                        Intent(requireContext(),  HomeActivity::class.java)
                    )
                }
            }, onError = {
                response ->
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}