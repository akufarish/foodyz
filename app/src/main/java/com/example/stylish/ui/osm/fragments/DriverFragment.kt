package com.example.stylish.ui.osm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.data.model.Order
import com.example.stylish.data.model.UpdateStatusOrder
import com.example.stylish.databinding.FragmentDriverBinding
import com.example.stylish.utils.formatCurrency
import com.example.stylish.viewmodel.AuthViewModel
import com.example.stylish.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverFragment : Fragment() {

    private var _binding: FragmentDriverBinding? = null
    private val binding get() = _binding!!
    private lateinit var order: Order
    private val authViewModel: AuthViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()

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
        _binding = FragmentDriverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(order: Order): DriverFragment {
            val fragment = DriverFragment()
            val args = Bundle()
            args.putParcelable("order",order)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("data_order", order.toString())
        setData()
    }

    private fun setData() {
        binding.nameTextView.text = order.drivers?.name
        binding.emailTextView.text = order.drivers?.email
        binding.alamatTextView.text = order.location
        var totalHarga = 0

        for (menu in order.detail_order) {
            totalHarga += menu.menu.price.toInt() * menu.stok.toInt() + (1000 * 4)
            binding.hargaTextView.text = formatCurrency(totalHarga)
            binding.ongkirTextView.text = formatCurrency(1000 * 4)
            binding.totalTextView.text = formatCurrency(totalHarga)
        }
        Log.d("login_role", authViewModel.getRole().toString())

        val role = authViewModel.getRole()

        if (role == "driver") {
            binding.ambilPesananButton.visibility = View.VISIBLE

            binding.ambilPesananButton.setOnClickListener {
                val payload = UpdateStatusOrder(
                    _method = "PUT",
                    is_diambil = true,
                    is_done = false
                )
                orderViewModel.updateStatusOrder(order.id.toInt(), payload, onSuccess = {
                    response ->
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }, onError = {
                     response ->
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()

                })
            }
        }

        if (order.is_diambil == true) {
            binding.ambilPesananButton.visibility = View.GONE
            binding.selesaiButton.visibility = View.VISIBLE
            binding.selesaiButton.setOnClickListener {
                val payload = UpdateStatusOrder(
                    _method = "PUT",
                    is_diambil = true,
                    is_done = true
                )
                orderViewModel.updateStatusOrder(order.id.toInt(), payload, onSuccess = {
                        response ->
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }, onError = {
                        response ->
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()

                })
            }
        }
    }
}