package com.sv.calorieintakeapps.feature_reporting.domain.usecase

import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

class ReportingInteractor(private val reportingRepository: IReportingRepository) :
    ReportingUseCase {
    
    override suspend fun addReport(
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
        return if (postImageFile == null) {
            reportingRepository.addReportToDb(
                foodId = foodId,
                date = date,
                time = time,
                percentage = percentage,
                mood = mood,
                preImageFile = preImageFile,
                postImageFile = null,
                nilaigiziComFoodId = nilaigiziComFoodId,
                portionCount = portionCount,
                foodName = foodName,
                portionSize = portionSize,
                merchantId = merchantId,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
            )
        } else {
            reportingRepository.addReport(
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
                merchantId = merchantId,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
            )
        }
    }

    override suspend fun getReportById(reportId: Int, isFromLocalDb: Boolean): Flow<Resource<Report>> {
        return if (isFromLocalDb) {
            reportingRepository.getReportByIdFromLocalDb(reportId)
        } else {
            reportingRepository.getReportById(reportId)
        }
    }
    
    override suspend fun editReportById(
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
        foodName: String,
        portionSize: String?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?,
        isFromLocalDb: Boolean,
    ): Flow<Resource<Boolean>> {
        return if (isFromLocalDb) {
            reportingRepository.editReportToDb(
                roomId = reportId,
                foodId = foodId,
                date = date,
                time = time,
                percentage = percentage,
                mood = mood,
                preImageFile = preImageFile,
                postImageFile = null,
                nilaigiziComFoodId = nilaigiziComFoodId,
                portionCount = portionCount,
                foodName = foodName,
                portionSize = portionSize,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
            )
        } else {
            reportingRepository.editReportById(
                reportId = reportId,
                date = date,
                time = time,
                percentage = percentage,
                mood = mood,
                preImageFile = preImageFile,
                postImageFile = postImageFile,
                foodId = foodId,
                nilaigiziComFoodId = nilaigiziComFoodId,
                portionCount = portionCount
            )
        }
    }
    
    override fun deleteReportById(reportId: Int): Flow<Resource<Boolean>> {
        return reportingRepository.deleteReportById(reportId)
    }
    
}