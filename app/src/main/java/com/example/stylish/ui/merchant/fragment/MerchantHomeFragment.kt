package com.example.stylish.ui.merchant.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.stylish.R
import com.example.stylish.adapter.MenuAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.FragmentMerchantHomeBinding
import com.example.stylish.ui.home.activity.DetailMakananActivity
import com.example.stylish.viewmodel.merchant.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantHomeFragment : Fragment(), MenuViewModel.onMenuClickListener {

    private var _binding: FragmentMerchantHomeBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMerchantHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        setupRv()
        getMenu()
        menuViewModel.menu.observe(this) {response ->
            menuAdapter.setData(response.data)
        }
        binding.seeAllImageButton.setOnClickListener {
            findNavController().navigate(R.id.allMenuFragment)
        }
    }

    private fun setupRv() {
        menuAdapter = MenuAdapter(this)
        binding.makananRecyclerView.adapter = menuAdapter
    }

    private fun getMenu() {
        menuViewModel.getMenu(onSuccess = { response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        }, onError = { response ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDetailClick(menu: Menu) {
        val intent = Intent(requireContext(), DetailMakananActivity::class.java)
        intent.putExtra("id", menu.id)
        startActivity(intent)
    }


}