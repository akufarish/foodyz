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
import com.example.stylish.data.model.Menu
import com.example.stylish.databinding.ActivityDetailMakananBinding
import com.example.stylish.models.Food
import com.example.stylish.ui.osm.OsmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMakananActivity : AppCompatActivity() {
    private var _binding: ActivityDetailMakananBinding? = null
    private val binding get() = _binding

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

        setData()
    }

    fun setData() {
        val menu = intent.getParcelableExtra<Menu>("menu")
        Log.d("data_detail_menu", menu.toString())

        menu.let {
            binding?.foodTitle?.text = menu?.name
            binding?.foodLocation?.text = menu?.merchants?.address
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}