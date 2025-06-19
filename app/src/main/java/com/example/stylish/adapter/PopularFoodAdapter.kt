package com.example.stylish.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.databinding.FoodItemBinding
import com.example.stylish.models.Food

class PopularFoodAdapter(
    private val foodData: ArrayList<Food>, private  val context: Context
): RecyclerView.Adapter<PopularFoodAdapter.PopularFoodAdapterViewHolder>() {
    class PopularFoodAdapterViewHolder(val binding: FoodItemBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFoodAdapterViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PopularFoodAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodData.size
    }

    override fun onBindViewHolder(holder: PopularFoodAdapterViewHolder, position: Int) {
        val currentItem = foodData[position]
        holder.binding.foodImage.setImageResource(currentItem.image)
        holder.binding.foodTitle.text = currentItem.title
        holder.binding.foodLocation.text = currentItem.location
    }
}