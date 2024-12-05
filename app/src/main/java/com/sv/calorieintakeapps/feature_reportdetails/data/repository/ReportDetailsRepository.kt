package com.sv.calorieintakeapps.feature_reportdetails.data.repository

import com.sv.calorieintakeapps.feature_reportdetails.domain.repository.IReportDetailsRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

class ReportDetailsRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IReportDetailsRepository {

    override fun getReportById(reportId: Int): Flow<Resource<ReportDomainModel>> {
        return object : NetworkBoundResource<ReportDomainModel, ReportResponse>() {
            private var resultDB = ReportDomainModel()

            override fun loadFromDB(): Flow<ReportDomainModel> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: ReportDomainModel?): Boolean {
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

    override suspend fun getReportByIdFromLocalDb(reportId: Int): Flow<Resource<ReportDomainModel>> {
        val reportEntity = localDataSource.getReportById(reportId) ?: return flowOf(Resource.Error("Report not found", null))
        val report = ReportDomainModel(
            id = reportEntity.id ?: -1,
            userId = -1,
            foodName = reportEntity.foodName.orEmpty(),
            date = reportEntity.date.orEmpty(),
            percentage = reportEntity.percentage ?: 0,
            mood = reportEntity.mood.orEmpty(),
            preImageFile = File(reportEntity.preImageFilePath ?: ""),
            postImageFile = File(reportEntity.postImageFilePath ?: ""),
            air = reportEntity.air,
            calories = reportEntity.calories,
            carbs = reportEntity.carbs,
            fat = reportEntity.fat,
            protein = reportEntity.protein,
            gramPerUrt = reportEntity.gramPerUrt,
            gramTotalDikonsumsi = reportEntity.gramTotalDikonsumsi,
            isUsingUrt = reportEntity.isUsingUrt,
            porsiUrt = reportEntity.porsiUrt,
            idMakananNewApi = reportEntity.idMakanananNewApi,
        )
        return flowOf(Resource.Success(report))
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