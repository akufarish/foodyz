package com.example.stylish.ui.driver.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.adapter.DriverOrderAdapter
import com.example.stylish.data.model.DriverGetOrderRequest
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.FragmentHomeDriverBinding
import com.example.stylish.ui.osm.OsmActivity
import com.example.stylish.viewmodel.LocationViewModel
import com.example.stylish.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDriverFragment : Fragment(), OrderViewModel.onMenuClickListener {

    private var _binding: FragmentHomeDriverBinding? = null
    private val binding get() = _binding!!
    private val locationViewModel: LocationViewModel by viewModels()
    private var currentLokasi: String? = null
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var driverOrderAdapter: DriverOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeDriverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        currentLokasi = locationViewModel.getLokasi()
        setupRv()
        getData()
        orderViewModel.allOrder.observe(this) {response ->
            driverOrderAdapter.setData(response)
            binding.swipeRefresh.isRefreshing = false
        }
        binding.currentLocation.text = locationViewModel.getKota()
        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        getData()
    }

    private fun setupRv() {
        driverOrderAdapter = DriverOrderAdapter(this)
        binding.keranjangItemRecyclerView.adapter = driverOrderAdapter
    }

    private fun getData() {
        val parts = currentLokasi?.split(",")
        val result = parts?.get(2)?.trim() + ", " + parts?.get(3)?.trim()
        Log.d("lokasi_splig", result)
        val payload = DriverGetOrderRequest(
            result
        )
        orderViewModel.driverGetOrder(payload, onSuccess = {
            response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            Log.d("orderan", response.toString())
            Log.d("orderan", payload.toString())
        }, onError = {
            rseponse ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDetailClick(order: Order) {
        if (order.is_taken == true) {
            val intent = Intent(requireContext(), OsmActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        } else {
            orderViewModel.driverTakeOrder(order.id.toInt(), onSuccess = {
                    response ->
                Log.d("orderan", response.toString())
                Log.d("id_orderan", response.id.toString())
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            }, onError = {
                    resposne ->
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            })
        }
        }
}