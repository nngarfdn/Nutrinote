package com.sv.calorieintakeapps.feature_reportdetails.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IReportDetailsRepository {

    fun getReportById(reportId: Int): Flow<Resource<Report>>

    suspend fun getReportByIdFromLocalDb(reportId: Int): Flow<Resource<Report>>

    fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>>
}