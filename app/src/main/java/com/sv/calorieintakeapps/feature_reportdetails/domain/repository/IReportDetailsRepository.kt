package com.sv.calorieintakeapps.feature_reportdetails.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IReportDetailsRepository {

    fun getReportById(reportId: Int): Flow<Resource<ReportDomainModel>>

    suspend fun getReportByIdFromLocalDb(reportId: Int): Flow<Resource<ReportDomainModel>>

    fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>>
}