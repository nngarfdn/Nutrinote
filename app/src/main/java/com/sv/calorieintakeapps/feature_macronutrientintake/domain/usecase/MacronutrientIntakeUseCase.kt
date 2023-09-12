package com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MacronutrientIntakeUseCase {
    
    fun getInputDate(): Flow<Resource<List<String>>>
    
    fun getReportByDate(date: String): Flow<Resource<List<Report>>>
    
    fun getMacronutrientIntakePercentage(
        reports: List<Report>,
    ): Flow<Resource<MacronutrientIntakePercentage>>
    
}