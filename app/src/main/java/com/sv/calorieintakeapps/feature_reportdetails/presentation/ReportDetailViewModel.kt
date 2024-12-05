package com.sv.calorieintakeapps.feature_reportdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.sv.calorieintakeapps.feature_reportdetails.domain.usecase.ReportDetailsUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.collectLatest

class ReportDetailViewModel(val useCase: ReportDetailsUseCase) : ViewModel() {

    fun getReportById(reportId: Int, isFromLocalDb: Boolean): LiveData<Resource<ReportDomainModel>> = liveData {
        useCase.getReportById(reportId, isFromLocalDb).collectLatest {
            emit(it)
        }
    }

    fun getFoodNutrientsById(foodId: Int): LiveData<Resource<List<FoodNutrient>>> =
        useCase.getFoodNutrientsById(foodId).asLiveData()

}