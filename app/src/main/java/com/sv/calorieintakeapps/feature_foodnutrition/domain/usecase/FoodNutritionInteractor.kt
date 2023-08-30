package com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase

import androidx.paging.PagingData
import com.sv.calorieintakeapps.feature_foodnutrition.domain.repository.IFoodNutritionRepository
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class FoodNutritionInteractor(
    private val foodNutritionSearchRepository: IFoodNutritionRepository,
) : FoodNutritionUseCase {
    
    override fun search(query: String): Flow<PagingData<FoodNutrition>> {
        val pageSize = 10
        return foodNutritionSearchRepository.search(
            query = query,
            pageSize = pageSize,
        )
    }
    
    override fun getDetails(foodId: Int): Flow<Resource<FoodNutrition>> {
        return foodNutritionSearchRepository.getDetails(foodId)
    }
    
}