package com.sv.calorieintakeapps.feature_homepage.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface HomepageUseCase {

    fun isLoggedIn(): Flow<Boolean>

    fun getUserName(): Flow<String>

    fun getUserCompletedReports(): Flow<Resource<List<Report>>>

    fun getUserPendingReports(): Flow<Resource<List<Report>>>

    fun logout()
}