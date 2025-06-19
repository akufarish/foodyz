package com.example.stylish.ui.home.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylish.R
import com.example.stylish.adapter.FoodAdapter
import com.example.stylish.databinding.ActivityDetailMakananBinding
import com.example.stylish.models.Food
import com.example.stylish.ui.osm.OsmActivity

class DetailMakananActivity : AppCompatActivity() {
    private var _binding: ActivityDetailMakananBinding? = null
    private val binding get() = _binding

    private val foodImage = arrayListOf<Int>(
        R.drawable.food1,
        R.drawable.food1,
        R.drawable.food1,
    )
    private val foodTitle = arrayListOf<String>(
        "Nasi Goreng",
        "Nasi Goreng",
        "Nasi Goreng",
    )

    private val foodLocation = arrayListOf<String>(
        "Indonesia",
        "Indonesia",
        "Indonesia",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailMakananBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val foodItem = arrayListOf<Food>()
        for (i in foodImage.indices) {
            val food = Food(
                image = foodImage[i],
                title = foodTitle[i],
                location = foodLocation[i]
            )
            foodItem.add(food)
        }
        val foodAdapter: FoodAdapter = FoodAdapter(foodItem, this@DetailMakananActivity, object : FoodAdapter.OnAdepterListener {
            override fun onClick(result: Food) {
                startActivity(
                    Intent(this@DetailMakananActivity, DetailMakananActivity::class.java)
                )
                Log.d("home fragment", "Text :")
            }
        })

        binding?.hotelRecyclerView?.apply {
            adapter = foodAdapter
        }

        binding?.keranjangButton?.setOnClickListener {
            startActivity(
                Intent(this@DetailMakananActivity, OsmActivity::class.java)
            )
        }
    }
}