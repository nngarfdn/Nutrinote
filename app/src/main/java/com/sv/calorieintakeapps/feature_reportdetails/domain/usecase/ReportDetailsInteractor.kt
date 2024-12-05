package com.sv.calorieintakeapps.feature_reportdetails.domain.usecase

import com.sv.calorieintakeapps.feature_reportdetails.domain.repository.IReportDetailsRepository
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class ReportDetailsInteractor(private val reportDetailsRepository: IReportDetailsRepository) :
    ReportDetailsUseCase {

    override suspend fun getReportById(reportId: Int, isFromLocalDb: Boolean): Flow<Resource<ReportDomainModel>> {
        return if (isFromLocalDb) {
            reportDetailsRepository.getReportByIdFromLocalDb(reportId)
        } else {
            reportDetailsRepository.getReportById(reportId)
        }
    }

    override fun getFoodNutrientsById(foodId: Int): Flow<Resource<List<FoodNutrient>>> {
        return reportDetailsRepository.getFoodNutrientsById(foodId)
    }
}