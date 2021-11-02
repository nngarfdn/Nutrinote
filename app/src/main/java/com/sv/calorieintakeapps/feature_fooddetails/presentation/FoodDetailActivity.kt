package com.sv.calorieintakeapps.feature_fooddetails.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.library_common.util.loadImage
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.databinding.ActivityFoodDetailsBinding
import com.sv.calorieintakeapps.feature_fooddetails.di.FoodDetailsModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_database.ui.adapter.FoodNutrientAdapter
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailsBinding

    private val viewModel: FoodDetailViewModel by viewModel()
    private lateinit var foodNutrientAdapter: FoodNutrientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FoodDetailsModule.load()

        val foodId = intent.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
        val foodName = intent.getStringExtra(Actions.EXTRA_FOOD_NAME).orEmpty()
        val foodImage = intent.getStringExtra(Actions.EXTRA_FOOD_IMAGE).orEmpty()

        binding.apply {
            btnLaporkan.setOnClickListener {
                startActivity(Actions.openReportingIntent(applicationContext, foodId, foodName))
            }
            imgDetailFood.loadImage(foodImage)
            judul.text = foodName
            btnBack.setOnClickListener { onBackPressed() }
        }

        viewModel.setFoodId(foodId)
        observeFoodNutrients()
    }

    private fun observeFoodNutrients() {
        viewModel.foodNutrients.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        binding.apply {
                            foodNutrientAdapter = FoodNutrientAdapter()
                            binding.apply {
                                rvNutrisi.apply {
                                    layoutManager = LinearLayoutManager(context)
                                    setHasFixedSize(true)
                                    foodNutrientAdapter.submitList(result.data)
                                    adapter = foodNutrientAdapter
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FoodDetailsModule.unload()
    }
}