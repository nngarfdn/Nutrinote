package com.sv.calorieintakeapps.feature_reportdetails.domain.usecase

import com.sv.calorieintakeapps.feature_reportdetails.domain.repository.IReportDetailsRepository
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class ReportDetailsInteractor(private val reportDetailsRepository: IReportDetailsRepository) :
    ReportDetailsUseCase {

    override fun getReportById(reportId: Int): Flow<Resource<Report>> {
        return reportDetailsRepository.getReportById(reportId)
    }

    override fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>> {
        return reportDetailsRepository.getFoodNutrientsById(foodId)
    }
}