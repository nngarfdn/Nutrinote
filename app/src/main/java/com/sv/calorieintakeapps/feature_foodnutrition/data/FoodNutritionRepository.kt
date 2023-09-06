package com.sv.calorieintakeapps.feature_foodnutrition.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_EMAIL
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_PASSWORD
import com.sv.calorieintakeapps.feature_foodnutrition.domain.repository.IFoodNutritionRepository
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.FoodNutritionSearchPagingSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.NilaigiziComLoginResponse
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FoodNutritionRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val foodNutritionSearchPagingSource: FoodNutritionSearchPagingSource,
) : IFoodNutritionRepository {
    
    override fun search(
        query: String,
        pageSize: Int,
    ): Flow<PagingData<FoodNutrition>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                prefetchDistance = 4,
            ),
            pagingSourceFactory = {
                foodNutritionSearchPagingSource.apply {
                    setParams(
                        query = query,
                        token = "Bearer ${localDataSource.getToken()}",
                    )
                }
            }
        ).flow.map { pagingData ->
            pagingData.map {
                mapResponseToDomain(it)
            }
        }
    }
    
    override fun getDetails(foodId: Int): Flow<Resource<FoodNutrition>> {
        return object : NetworkBoundResource<FoodNutrition, FoodNutritionDetailsResponse>() {
            private var resultDB = FoodNutrition(
                foodId = -1,
                name = "",
                imageUrl = "",
                calories = "",
                protein = "",
                fat = "",
                carbs = "",
            )
            
            override fun loadFromDB(): Flow<FoodNutrition> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: FoodNutrition?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<FoodNutritionDetailsResponse>> {
                return remoteDataSource.getFoodNutritionDetails(
                    foodId = foodId,
                    token = "Bearer ${localDataSource.getToken()}",
                )
            }
            
            override suspend fun saveCallResult(data: FoodNutritionDetailsResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
    
    override fun nilaigiziComLogin(): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, NilaigiziComLoginResponse>() {
            private var resultDB = false
            
            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<NilaigiziComLoginResponse>> {
                return remoteDataSource.nilaigiziComLogin(
                    email = NILAIGIZI_COM_EMAIL,
                    password = NILAIGIZI_COM_PASSWORD,
                )
            }
            
            override suspend fun saveCallResult(data: NilaigiziComLoginResponse) {
                resultDB = data.data?.token != null
                localDataSource.storeToken(
                    token = data.data?.token.orEmpty()
                )
            }
        }.asFlow()
    }
    
}