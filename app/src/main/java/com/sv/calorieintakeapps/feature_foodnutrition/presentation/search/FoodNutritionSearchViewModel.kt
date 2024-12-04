package com.sv.calorieintakeapps.feature_foodnutrition.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FoodNutritionSearchViewModel(
    private val foodNutritionUseCase: FoodNutritionUseCase,
) : ViewModel() {
    
    fun search(query: String): Flow<PagingData<FoodNutrition>> {
        return foodNutritionUseCase.search(query)
    }
    
    fun nilaigiziComLogin() {
        viewModelScope.launch {
            foodNutritionUseCase.nilaigiziComLogin().collect()
        }
    }

    private val foodId = MutableLiveData<Int>()

    fun setFoodId(foodId: Int) {
        this.foodId.value = foodId
    }

    val foodNutrition: LiveData<Resource<FoodNutrition>> =
        foodId.switchMap {
            foodNutritionUseCase.getDetails(it).asLiveData()
        }
    
}