package com.sv.calorieintakeapps.feature_foodnutrition.domain.repository

import androidx.paging.PagingData
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IFoodNutritionRepository {
    
    fun search(
        query: String,
        pageSize: Int,
    ): Flow<PagingData<FoodNutrition>>
    
    fun getDetails(foodId: Int): Flow<Resource<FoodNutrition>>
    
    fun nilaigiziComLogin(): Flow<Resource<Boolean>>
    
}