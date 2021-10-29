package com.sv.calorieintakeapps.feature_fooddetails.repository

import com.sv.calorieintakeapps.feature_fooddetails.domain.repository.IFoodDetailsRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FoodDetailsRepository(
    private val remoteDataSource: RemoteDataSource
) : IFoodDetailsRepository {

    override fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>> {
        return object : NetworkBoundResource<List<FoodNutrient>, FoodNutrientsResponse>() {
            private var resultDB = listOf<FoodNutrient>()

            override fun loadFromDB(): Flow<List<FoodNutrient>> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: List<FoodNutrient>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<FoodNutrientsResponse>> {
                return remoteDataSource.getFoodNutrientsById(foodId)
            }

            override suspend fun saveCallResult(data: FoodNutrientsResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
}