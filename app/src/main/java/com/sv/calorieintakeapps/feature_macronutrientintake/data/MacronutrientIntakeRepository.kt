package com.sv.calorieintakeapps.feature_macronutrientintake.data

import com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository.IMacronutrientIntakeRepository
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.UserResponse
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MacronutrientIntakeRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IMacronutrientIntakeRepository {
    
    override fun getUserReports(): Flow<Resource<List<ReportDomainModel>>> {
        return object : NetworkBoundResource<List<ReportDomainModel>, ReportsResponse>() {
            private var resultDB = listOf<ReportDomainModel>()
            
            override fun loadFromDB(): Flow<List<ReportDomainModel>> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: List<ReportDomainModel>?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<ReportsResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getReportsByUserId(userId)
            }
            
            override suspend fun saveCallResult(data: ReportsResponse) {
                val reports = mapResponseToDomain(data)
                resultDB = reports
            }
        }.asFlow()
    }
    
    override fun getFoodNutrientsByFoodIds(foodIds: List<Int>): Flow<Resource<List<FoodNutrient>>> {
        return object : NetworkBoundResource<List<FoodNutrient>, List<FoodNutrientsResponse>>() {
            private var resultDB = listOf<FoodNutrient>()
            
            override fun loadFromDB(): Flow<List<FoodNutrient>> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: List<FoodNutrient>?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<List<FoodNutrientsResponse>>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getFoodNutrientsByFoodIds(foodIds, userId)
            }
            
            override suspend fun saveCallResult(data: List<FoodNutrientsResponse>) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
    
    override fun getUserProfile(): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            private var resultDB = User()
            
            override fun loadFromDB(): Flow<User> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: User?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getUserProfile(userId)
            }
            
            override suspend fun saveCallResult(data: UserResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
    
}