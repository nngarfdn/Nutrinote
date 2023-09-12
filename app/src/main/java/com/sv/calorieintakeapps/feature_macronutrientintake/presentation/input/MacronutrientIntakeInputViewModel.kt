package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase.MacronutrientIntakeUseCase
import com.sv.calorieintakeapps.library_database.vo.Resource

class MacronutrientIntakeInputViewModel(useCase: MacronutrientIntakeUseCase) : ViewModel() {
    
    var date: String? = null
    
    val inputDate: LiveData<Resource<List<String>>> =
        useCase.getInputDate().asLiveData()
    
}