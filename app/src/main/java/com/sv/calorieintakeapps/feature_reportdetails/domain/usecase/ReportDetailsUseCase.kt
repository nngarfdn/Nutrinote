package com.sv.calorieintakeapps.feature_reportdetails.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ReportDetailsUseCase {

    suspend fun getReportById(reportId: Int, isFromLocalDb: Boolean): Flow<Resource<Report>>

    fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>>
}