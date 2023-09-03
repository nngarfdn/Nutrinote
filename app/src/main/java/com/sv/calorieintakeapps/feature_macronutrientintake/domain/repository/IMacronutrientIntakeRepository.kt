package com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IMacronutrientIntakeRepository {
    
    fun getUserReports(): Flow<Resource<List<Report>>>
    
    fun getFoodNutrientsByFoodIds(foodIds: List<Int>): Flow<Resource<List<FoodNutrient>>>
    
    fun getUserProfile(): Flow<Resource<User>>
    
}