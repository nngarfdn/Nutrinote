package com.sv.calorieintakeapps.feature_fooddetails.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IFoodDetailsRepository {

    fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>>
}