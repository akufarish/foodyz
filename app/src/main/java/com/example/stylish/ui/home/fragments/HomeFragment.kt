package com.example.stylish.ui.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.stylish.R
import com.example.stylish.adapter.CarouselAdapter
import com.example.stylish.adapter.FoodAdapter
import com.example.stylish.adapter.PopularFoodAdapter
import com.example.stylish.databinding.FragmentHomeBinding
import com.example.stylish.models.Carousel
import com.example.stylish.models.Food

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

    private val image = arrayListOf<Int>(
        R.drawable.carousel,
        R.drawable.carousel,
        R.drawable.carousel,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val foodItem = arrayListOf<Food>()
        val popularFoodAdapter: PopularFoodAdapter = PopularFoodAdapter(foodItem, requireContext())

        for (i in foodImage.indices) {
            val food = Food(
                image = foodImage[i],
                title = foodTitle[i],
                location = foodLocation[i]
            )
            foodItem.add(food)
        }

        binding.foodRecyclerView.apply {
            adapter = popularFoodAdapter
        }

        val foodAdapter: FoodAdapter = FoodAdapter(foodItem, requireContext(), object : FoodAdapter.OnAdepterListener {
            override fun onClick(result: Food) {
                Log.d("home fragment", "Text :")
            }
        })

        binding.hotelRecyclerView.apply {
            adapter = foodAdapter
        }

        val carouselItem = arrayListOf<Carousel>()

        for (i in image.indices) {
            val carousel = Carousel(image = image[i])
            carouselItem.add(carousel)
        }

        val carouselAdapter = CarouselAdapter(carouselItem, requireContext())

        binding.apply {
            binding.homeCarousel.apply {
                adapter = carouselAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                homeDotIndicator.attachTo(binding.homeCarousel)
            }
        }

    }
}