package com.sv.calorieintakeapps.feature_fooddetails.domain.usecase

import com.sv.calorieintakeapps.feature_fooddetails.domain.repository.IFoodDetailsRepository
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class FoodDetailsInteractor(private val foodDetailsRepository: IFoodDetailsRepository) :
    FoodDetailsUseCase {

    override fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>> {
        return foodDetailsRepository.getFoodNutrientsById(foodId)
    }
}