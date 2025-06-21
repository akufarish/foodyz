package com.example.stylish.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.HotelItemBinding
import com.example.stylish.viewmodel.merchant.MenuViewModel

class MenuAdapter (private val listener: MenuViewModel.onMenuClickListener): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val menu = mutableListOf<Menu>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Menu>) {
        menu.clear()
        menu.addAll(newData)
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(private val binding: HotelItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            binding.foodLocation.text = menu.price
            binding.hotelTitle.text = menu.name
            binding.bookingButton.text = "Edit"

            binding.detailLayout.setOnClickListener {
                listener.onDetailClick(menu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = HotelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menu[position])
    }
}