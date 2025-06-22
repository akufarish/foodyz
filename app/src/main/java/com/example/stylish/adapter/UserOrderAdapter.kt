package com.example.stylish.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.Order
import com.example.stylish.databinding.FragmentKeranjangBinding
import com.example.stylish.databinding.HotelItemBinding
import com.example.stylish.utils.formatCurrency
import com.example.stylish.viewmodel.OrderViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class UserOrderAdapter @Inject constructor(
    private val listener: OrderViewModel.onMenuClickListener,
    @ActivityContext private val context: Context
): RecyclerView.Adapter<UserOrderAdapter.ViewHolder>() {
    private val data = mutableListOf<Order>()
    var fragmentKeranjangBinding: FragmentKeranjangBinding? = null
    private var totalHarga = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: Order) {
        data.clear()
        data.add(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: HotelItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Order) {
            binding.foodPrice.visibility = View.VISIBLE
            binding.foodStock.visibility = View.VISIBLE
            for (menu in data.detail_order) {
                binding.hotelTitle.text = menu.menu.name
                binding.foodStock.text = "${menu.stok}x"
                binding.bookingButton.text = "Hapus"

                totalHarga += menu.menu.price.toInt() * menu.stok.toInt()
                Log.d("total_harga", totalHarga.toString())
                binding.foodPrice.text = formatCurrency(totalHarga)
                fragmentKeranjangBinding?.totalHargaTextView?.text = formatCurrency(totalHarga)
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