package com.sv.calorieintakeapps.feature_reportdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sv.calorieintakeapps.feature_reportdetails.domain.usecase.ReportDetailsUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource

class ReportDetailViewModel(val useCase: ReportDetailsUseCase) : ViewModel() {

    fun getReportById(reportId: Int): LiveData<Resource<Report>> =
        useCase.getReportById(reportId).asLiveData()

    fun getFoodNutrientsById(foodId: Int): LiveData<Resource<List<FoodNutrient>>> =
        useCase.getFoodNutrientsById(foodId).asLiveData()

}