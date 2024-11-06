package com.sv.calorieintakeapps.feature_reporting.data.repository

import android.util.Log
import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportEntity
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.Response
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.ReportBuilder
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

class ReportingRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IReportingRepository {
    
    override fun addReport(
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
        foodName: String,
        portionSize: String?,
        merchantId: Int?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?,
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, ReportResponse>() {
            private var resultDB = false
            
            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<ReportResponse>> {
                val userId = localDataSource.getUserId()
                val report = ReportBuilder.create(
                    userId = userId,
                    foodId = foodId,
                    date = date,
                    time = time,
                    percentage = percentage,
                    mood = mood,
                    preImageFile = preImageFile,
                    postImageFile = postImageFile,
                    nilaigiziComFoodId = nilaigiziComFoodId,
                    portionCount = portionCount,
                )
                return remoteDataSource.addReport(
                    report = report,
                    foodName = foodName,
                    portionSize = portionSize,
                    merchantId = merchantId,
                    calories = calories,
                    protein = protein,
                    fat = fat,
                    carbs = carbs,
                )
            }
            
            override suspend fun saveCallResult(data: ReportResponse) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }
    
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
    
    override fun editReportById(
        reportId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        foodId: Int?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, Response>() {
            private var resultDB = false
            
            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<Response>> {
                val userId = localDataSource.getUserId()
                val report = ReportBuilder.update(
                    id = reportId,
                    userId = userId,
                    date = date,
                    time = time,
                    percentage = percentage,
                    mood = mood,
                    preImageFile = preImageFile,
                    postImageFile = postImageFile,
                    foodId = foodId,
                    nilaigiziComFoodId = nilaigiziComFoodId,
                    portionCount = portionCount,
                )
                return remoteDataSource.editReportById(report)
            }
            
            override suspend fun saveCallResult(data: Response) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }
    
    override fun deleteReportById(reportId: Int): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, Response>() {
            private var resultDB = false
            
            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<Response>> {
                return remoteDataSource.deleteReportById(reportId)
            }
            
            override suspend fun saveCallResult(data: Response) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }

    override suspend fun addReportToDb(
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
        foodName: String,
        portionSize: String?,
        merchantId: Int?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?
    ): Flow<Resource<Boolean>> {
        val userId = localDataSource.getUserId()
        val reportEntity = ReportBuilder.createReportEntity(
            userId = userId,
            foodId = foodId,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
            foodName = foodName,
            portionSize = portionSize,
            calories = calories,
            protein = protein,
            fat = fat,
            carbs = carbs,
        )
        Log.d("FIKRI421", reportEntity.preImageFilePath.toString())
        val result = localDataSource.insertReport(reportEntity)
        return flowOf(Resource.Success(result > 0))
    }

    override suspend fun editReportToDb(
        roomId: Int,
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
        foodName: String,
        portionSize: String?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?
    ): Flow<Resource<Boolean>> {
        val userId = localDataSource.getUserId()
        val reportEntity = ReportBuilder.createReportEntity(
            userId = userId,
            foodId = foodId,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
            foodName = foodName,
            portionSize = portionSize,
            calories = calories,
            protein = protein,
            fat = fat,
            carbs = carbs,
        ).copy(roomId = roomId)

        val result = localDataSource.updateReport(reportEntity)
        return flowOf(Resource.Success(result > 0))
    }

    override suspend fun deleteReportByIdFromLocal(reportId: Int): Flow<Resource<Boolean>> {
        val result = localDataSource.deleteReportById(reportId)
        return flowOf(Resource.Success(result > 0))
    }

    override suspend fun getReportByIdFromLocalDb(reportId: Int): Flow<Resource<Report>> {
        val reportEntity = localDataSource.getReportById(reportId) ?: return flowOf(Resource.Error("Report not found", null))
        val preImageFile = if (!reportEntity.preImageFilePath.isNullOrEmpty()) File(reportEntity.preImageFilePath) else null
        val postImageFile = if (!reportEntity.postImageFilePath.isNullOrEmpty()) File(reportEntity.postImageFilePath) else null
        val report = Report(
            id = reportEntity.id ?: -1,
            userId = reportEntity.userId ?: -1,
            foodId = reportEntity.foodId ?: -1,
            foodName = reportEntity.foodName.orEmpty(),
            date = reportEntity.date.orEmpty(),
            status = ReportStatus.PENDING,
            percentage = reportEntity.percentage ?: 0,
            mood = reportEntity.mood.orEmpty(),
            nilaigiziComFoodId = reportEntity.nilaigiziComFoodId,
            portionCount = reportEntity.portionCount,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
        )
        return flowOf(Resource.Success(report))
    }
}