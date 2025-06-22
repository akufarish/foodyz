package com.example.stylish.ui.osm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stylish.R
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.FragmentMerchantBinding
import com.example.stylish.utils.formatCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantFragment : Fragment() {

    private var _binding: FragmentMerchantBinding? = null
    private val binding get() = _binding!!
    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
            order = requireArguments().getParcelable<Order>("order")!!
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMerchantBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(order: Order): MerchantFragment {
            val fragment = MerchantFragment()
            val args = Bundle()
            args.putParcelable("order",order)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        Log.d("data_order", order.toString())
        setData()
    }

    private fun setData() {
        binding.nameTextView.text = order.merchant?.name
        binding.emailTextView.text = order.merchant?.address
        binding.alamatTextView.text = order.location
        var totalHarga = 0

        for (menu in order.detail_order) {
            totalHarga += menu.menu.price.toInt() * menu.stok.toInt() + (1000 * 4)
            binding.hargaTextView.text = formatCurrency(totalHarga)
            binding.ongkirTextView.text = formatCurrency(1000 * 4)
            binding.totalTextView.text = formatCurrency(totalHarga)
        }
    }
}