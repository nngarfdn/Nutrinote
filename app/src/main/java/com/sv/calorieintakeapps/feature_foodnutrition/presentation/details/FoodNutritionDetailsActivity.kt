package com.sv.calorieintakeapps.feature_foodnutrition.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityFoodNutritionDetailsBinding
import com.sv.calorieintakeapps.feature_foodnutrition.di.FoodNutritionModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.action.Actions.openReportingIntent
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodNutritionDetailsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityFoodNutritionDetailsBinding
    private val viewModel: FoodNutritionDetailsViewModel by viewModel()
    
    private var merchantId: Int = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodNutritionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        FoodNutritionModule.load()
        
        val foodId = intent.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
        merchantId = intent.getIntExtra(Actions.EXTRA_MERCHANT_ID, -1)
        
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnReport.isEnabled = false
        }
        
        viewModel.setFoodId(foodId)
        observeFoodNutritionDetails()
    }
    
    private fun observeFoodNutritionDetails() {
        viewModel.foodNutrition.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        result.data?.let { food ->
                            binding.apply {
                                tvFoodName.text = food.name
                                tvCalories.text = food.calories
                                tvProtein.text = food.protein
                                tvFat.text = food.fat
                                tvCarbs.text = food.carbs
                                imgFood.load(food.imageUrl)
                                
                                btnReport.isEnabled = true
                                btnReport.setOnClickListener {
                                    startActivity(
                                        openReportingIntent(
                                            foodId = null,
                                            foodName = food.name,
                                            merchantId = if (merchantId < 0) null else merchantId,
                                            nilaigiziComFoodId = food.foodId,
                                            calories = food.calories.split(" ").first(),
                                            protein = food.protein.split(" ").first(),
                                            fat = food.fat.split(" ").first(),
                                            carbs = food.carbs.split(" ").first(),
                                            writeFoodName = true,
                                        )
                                    )
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
        FoodNutritionModule.unload()
    }
    
}