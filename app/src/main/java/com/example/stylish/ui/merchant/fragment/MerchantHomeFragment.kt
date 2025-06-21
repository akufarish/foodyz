package com.example.stylish.ui.merchant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stylish.R
import com.example.stylish.databinding.FragmentMerchantHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantHomeFragment : Fragment() {

    private var _binding: FragmentMerchantHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMerchantHomeBinding.inflate(layoutInflater, container ,false)
        return binding.root
    }

}