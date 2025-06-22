package com.example.stylish.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.adapter.UserOrderAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.FragmentKeranjangBinding
import com.example.stylish.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeranjangFragment : Fragment(), OrderViewModel.onMenuClickListener {

    private var _binding: FragmentKeranjangBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var userOrderAdapter: UserOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeranjangBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        setupRv()
        getCurrentData()
        orderViewModel.order.observe(this) {response ->
            userOrderAdapter.setData(response.detail_order)
        }
    }

    private fun setupRv() {
        userOrderAdapter = UserOrderAdapter(this, requireContext())
        userOrderAdapter.fragmentKeranjangBinding = _binding
        binding.keranjangItemRecyclerView.adapter = userOrderAdapter
    }

    private fun getCurrentData() {
        orderViewModel.getCurrentOrder(onSuccess = {
            response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        }, onError = {
            response ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDetailClick(order: Order) {
        TODO("Not yet implemented")
    }
}