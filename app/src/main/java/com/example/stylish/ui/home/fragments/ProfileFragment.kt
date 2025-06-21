package com.example.stylish.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.databinding.FragmentProfileBinding
import com.example.stylish.ui.auth.AuthActivity
import com.example.stylish.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        logout()
    }

    private fun logout() {
        binding.logoutButton.setOnClickListener {
            authViewModel.logout(onSuccess = {
                response ->
                startActivity(
                    Intent(requireContext(), AuthActivity::class.java)
                )
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