package com.example.stylish.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.databinding.HotelItemBinding
import com.example.stylish.models.Food

class FoodAdapter(
    private val foodData: ArrayList<Food>,
    private val context: Context,
    private val listener: OnAdepterListener
) : RecyclerView.Adapter<FoodAdapter.FoodAdapterViewHolder>() {
    class FoodAdapterViewHolder(val binding: HotelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapterViewHolder {
        val binding = HotelItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodData.size
    }

    override fun onBindViewHolder(holder: FoodAdapterViewHolder, position: Int) {
        val currentItem = foodData[position]
        holder.binding.hotelImage.setImageResource(currentItem.image)
        holder.binding.hotelTitle.text = currentItem.title
        holder.binding.foodLocation.text = currentItem.location

        holder.binding.bookingButton.setOnClickListener {
            listener.onClick(currentItem)
        }
    }

    interface OnAdepterListener {
        fun onClick(result: Food)
    }
}