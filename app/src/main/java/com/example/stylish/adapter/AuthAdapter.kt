package com.example.stylish.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stylish.ui.auth.login.LoginFragment
import com.example.stylish.ui.auth.register.RegisterFragment

class AuthAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> RegisterFragment()
            1 -> LoginFragment()
            else -> {
                RegisterFragment()
            }
        }

        return fragment
    }
}