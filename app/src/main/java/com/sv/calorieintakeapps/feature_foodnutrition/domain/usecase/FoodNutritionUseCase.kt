package com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase

import androidx.paging.PagingData
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface FoodNutritionUseCase {
    
    fun search(query: String): Flow<PagingData<FoodNutrition>>
    
    fun getDetails(foodId: Int): Flow<Resource<FoodNutrition>>
    
    fun nilaigiziComLogin(): Flow<Resource<Boolean>>

}