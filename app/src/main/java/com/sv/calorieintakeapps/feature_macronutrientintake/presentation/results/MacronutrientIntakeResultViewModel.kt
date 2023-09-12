package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase.MacronutrientIntakeUseCase
import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource

class MacronutrientIntakeResultViewModel(useCase: MacronutrientIntakeUseCase) : ViewModel() {
    
    private val date = MutableLiveData<String>()
    
    fun setInput(
        date: String,
    ) {
        this.date.value = date
    }
    
    val reports: LiveData<Resource<List<Report>>> =
        date.switchMap { date ->
            useCase.getReportByDate(date).asLiveData()
        }
    
    val macronutrientIntakePercentage: LiveData<Resource<MacronutrientIntakePercentage>> =
        reports.switchMap { result ->
            if (result is Resource.Success) {
                result.data?.let { reports ->
                    useCase.getMacronutrientIntakePercentage(
                        reports = reports,
                    ).asLiveData()
                }
            } else {
                MutableLiveData<Resource<MacronutrientIntakePercentage>>().apply {
                    value = Resource.Loading()
                }
            }
        }
    
}