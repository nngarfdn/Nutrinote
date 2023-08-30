package com.sv.calorieintakeapps.feature_foodnutrition.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.Resource

class FoodNutritionDetailsViewModel(
    private val foodNutritionUseCase: FoodNutritionUseCase,
) : ViewModel() {
    
    private val foodId = MutableLiveData<Int>()
    
    fun setFoodId(foodId: Int) {
        this.foodId.value = foodId
    }
    
    val foodNutrition: LiveData<Resource<FoodNutrition>> =
        foodId.switchMap {
            foodNutritionUseCase.getDetails(it).asLiveData()
        }
    
}