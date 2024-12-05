package com.sv.calorieintakeapps.feature_reporting.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ReportingUseCase {
    
    suspend fun addReport(
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
        gramTotalDikonsumsi: Float,
        isUsingUrt: Boolean,
        gramPerUrt: Float,
        porsiUrt: Int,
    ): Flow<Resource<Boolean>>
    
    suspend fun getReportById(reportId: Int, isFromLocalDb: Boolean): Flow<Resource<ReportDomainModel>>
    
    suspend fun editReportById(
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
    ): Flow<Resource<Boolean>>
    
    fun deleteReportById(reportId: Int): Flow<Resource<Boolean>>

    suspend fun deleteReportByIdFromLocal(reportId: Int): Flow<Resource<Boolean>>
}