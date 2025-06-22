package com.example.stylish.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.adapter.HistoryOrderAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.FragmentHistoryBinding
import com.example.stylish.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(), OrderViewModel.onMenuClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var historyOrderAdapter: HistoryOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        setupRv()
        getData()
        orderViewModel.allOrder.observe(this) {response ->
            historyOrderAdapter.setData(response)
        }
    }

    private fun getData() {
        orderViewModel.getAuthUserOrder(onSuccess = { response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        }, onError = { response ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRv() {
        historyOrderAdapter = HistoryOrderAdapter(this)
        binding.keranjangItemRecyclerView.adapter = historyOrderAdapter
    }

    override fun onDetailClick(order: Order) {
        TODO("Not yet implemented")
    }

}