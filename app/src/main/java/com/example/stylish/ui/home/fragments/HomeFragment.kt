package com.example.stylish.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.stylish.R
import com.example.stylish.adapter.CarouselAdapter
import com.example.stylish.adapter.FoodAdapter
import com.example.stylish.adapter.MenuAdapter
import com.example.stylish.adapter.PopularFoodAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.FragmentHomeBinding
import com.example.stylish.models.Carousel
import com.example.stylish.models.Food
import com.example.stylish.ui.home.activity.DetailMakananActivity
import com.example.stylish.viewmodel.LocationViewModel
import com.example.stylish.viewmodel.merchant.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuViewModel.onMenuClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var menuAdapter: PopularFoodAdapter
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupRv()
        getMenu()
        menuViewModel.menu.observe(this) { response ->
            menuAdapter.setData(response.data)
        }
        binding.currentLocation.text = locationViewModel.getKota()
    }

    private fun getMenu() {
        menuViewModel.getRandMenu(onSuccess = { response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        }, onError = { response ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRv() {
        menuAdapter = PopularFoodAdapter(this)
        binding.foodRecyclerView.adapter = menuAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetailClick(menu: Menu) {
        val intent = Intent(requireContext(), DetailMakananActivity::class.java)
        intent.putExtra("menu", menu)
        startActivity(intent)

    }
}