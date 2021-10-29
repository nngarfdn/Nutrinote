package com.sv.calorieintakeapps.feature_fooddetails.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface FoodDetailsUseCase {

    fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>>

}