package com.sv.calorieintakeapps.feature_reportdetails.data.repository

import com.sv.calorieintakeapps.feature_reportdetails.domain.repository.IReportDetailsRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.response.ReportResponse
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReportDetailsRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IReportDetailsRepository {

    override fun getReportById(reportId: Int): Flow<Resource<Report>> {
        return object : NetworkBoundResource<Report, ReportResponse>() {
            private var resultDB = Report()

            override fun loadFromDB(): Flow<Report> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Report?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<ReportResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getReportById(userId, reportId)
            }

            override suspend fun saveCallResult(data: ReportResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }

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
                val userId = localDataSource.getUserId()
                return remoteDataSource.getFoodNutrientsById(foodId, userId)
            }

            override suspend fun saveCallResult(data: FoodNutrientsResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
}