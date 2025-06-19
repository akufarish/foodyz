package com.example.stylish.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stylish.ui.auth.login.LoginFragment
import com.example.stylish.ui.auth.register.RegisterFragment
import com.example.stylish.ui.osm.fragments.DriverFragment
import com.example.stylish.ui.osm.fragments.MerchantFragment

class CurrentOrderAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> MerchantFragment()
            1 -> DriverFragment()
            else -> {
                MerchantFragment()
            }
        }

        return fragment
    }
}