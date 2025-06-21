package com.example.stylish.ui.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.data.model.RegisterRequest
import com.example.stylish.databinding.FragmentRegisterBinding
import com.example.stylish.ui.home.HomeActivity
import com.example.stylish.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
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
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun register() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val username = binding.usernameEmailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val merchant = binding.isMerchantCheckBox.isChecked
            val driver = binding.isDriverCheckBox.isChecked
            val payload = RegisterRequest(email, username, password, phone)
            authViewModel.register(email, password, username, phone, merchant, driver, onSuccess = {
                responses ->
                Toast.makeText(requireContext(), "Register berhasil", Toast.LENGTH_SHORT).show()
            }, onError = {
                responses ->
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onStart() {
        super.onStart()
        register()
        validasiCheckBox()
    }

    private fun validasiCheckBox() {
        binding.isMerchantCheckBox.setOnCheckedChangeListener{
                _, isChecked ->
            binding.isDriverCheckBox.isEnabled = !isChecked
        }
        binding.isDriverCheckBox.setOnCheckedChangeListener{
                _, isChecked ->
            binding.isMerchantCheckBox.isEnabled = !isChecked
        }
    }
}