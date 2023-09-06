package com.sv.calorieintakeapps.feature_foodnutrition.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
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
    
}