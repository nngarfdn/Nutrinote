package com.sv.calorieintakeapps.feature_reporting.domain.usecase

import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class ReportingInteractor(private val reportingRepository: IReportingRepository) :
    ReportingUseCase {
    
    override fun addReport(
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageUri: String,
        postImageUri: String,
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
        return reportingRepository.addReport(
            foodId = foodId,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageUri = preImageUri,
            postImageUri = postImageUri,
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
    
    override fun getReportById(reportId: Int): Flow<Resource<Report>> {
        return reportingRepository.getReportById(reportId)
    }
    
    override fun editReportById(
        reportId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageUri: String,
        postImageUri: String,
        foodId: Int?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ): Flow<Resource<Boolean>> {
        return reportingRepository.editReportById(
            reportId = reportId,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageUri = preImageUri,
            postImageUri = postImageUri,
            foodId = foodId,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount
        )
    }
    
    override fun deleteReportById(reportId: Int): Flow<Resource<Boolean>> {
        return reportingRepository.deleteReportById(reportId)
    }
    
}