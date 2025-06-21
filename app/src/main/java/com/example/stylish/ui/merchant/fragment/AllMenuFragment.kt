package com.example.stylish.ui.merchant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stylish.R
import com.example.stylish.adapter.MenuAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.FragmentAllMenuBinding
import com.example.stylish.viewmodel.merchant.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMenuFragment : Fragment(), MenuViewModel.onMenuClickListener {
    private var _binding: FragmentAllMenuBinding? = null
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
        _binding = FragmentAllMenuBinding.inflate(layoutInflater, container, false)
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
    }

    private fun getMenu() {
        menuViewModel.getMenu(onSuccess = { response ->
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        }, onError = { response ->
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRv() {
        menuAdapter = MenuAdapter(this)
        binding.makananRecyclerView.adapter = menuAdapter
    }

    override fun onDetailClick(menu: Menu) {
        TODO("Not yet implemented")
    }
}