package com.example.stylish.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.databinding.CarouselItemBinding
import com.example.stylish.models.Carousel

class CarouselAdapter(
    private val carouselData: ArrayList<Carousel>, private val context: Context
): RecyclerView.Adapter<CarouselAdapter.CarouselAdapterViewHolder>() {
    class CarouselAdapterViewHolder(val binding: CarouselItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselAdapterViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CarouselAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return carouselData.size
    }

    override fun onBindViewHolder(holder: CarouselAdapterViewHolder, position: Int) {
        val currentItem = carouselData[position]
        holder.binding.carouselImage.setImageResource(currentItem.image)
    }
}