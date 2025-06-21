package com.example.stylish.ui.merchant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.databinding.FragmentMerchantTambahMenuBinding
import com.example.stylish.viewmodel.merchant.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantTambahMenuFragment : Fragment() {

    private var _binding: FragmentMerchantTambahMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMerchantTambahMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        createMenu()
    }

    private fun createMenu() {
        binding.tambahButton.setOnClickListener {
            val name = binding.menuEditText.text.toString()
            val price = binding.priceEditText.text.toString()
            menuViewModel.createMenu(name, price, onSuccess = {
                response ->
                Toast.makeText(requireContext(), "berhasil!", Toast.LENGTH_SHORT).show()
            }, onError = {
                response ->
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            })
        }
    }

}