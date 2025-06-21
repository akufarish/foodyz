package com.example.stylish.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.FoodItemBinding
import com.example.stylish.models.Food
import com.example.stylish.viewmodel.merchant.MenuViewModel

class PopularFoodAdapter(
    private val listener: MenuViewModel.onMenuClickListener
): RecyclerView.Adapter<PopularFoodAdapter.PopularFoodAdapterViewHolder>() {
    private val menu = mutableListOf<Menu>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Menu>) {
        menu.clear()
        menu.addAll(newData)
        notifyDataSetChanged()
    }

    inner class PopularFoodAdapterViewHolder(val binding: FoodItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            binding.foodLocation.text = menu.price
            binding.foodTitle.text = menu.name

            binding.detailLayout.setOnClickListener {
                listener.onDetailClick(menu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFoodAdapterViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFoodAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: PopularFoodAdapterViewHolder, position: Int) {
        holder.bind(menu[position])
    }
}