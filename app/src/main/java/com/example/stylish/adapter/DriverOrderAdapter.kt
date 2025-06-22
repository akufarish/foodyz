package com.example.stylish.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.HotelItemBinding
import com.example.stylish.viewmodel.OrderViewModel
import javax.inject.Inject

class DriverOrderAdapter @Inject constructor(
    private val listener: OrderViewModel.onMenuClickListener
): RecyclerView.Adapter<DriverOrderAdapter.ViewHolder>() {
    private val data = mutableListOf<Order>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Order>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: HotelItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Order) {
            binding.hotelTitle.text = data.merchant?.name
            binding.foodLocation.text = data.location
            binding.bookingButton.text = data.status

            binding.detailLayout.setOnClickListener {
                listener.onDetailClick(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HotelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}