package com.sv.calorieintakeapps.feature_homepage.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface HomepageUseCase {

    fun isLoggedIn(): Flow<Boolean>

    fun getUserName(): Flow<String>

    fun getUserCompletedReports(): Flow<Resource<List<ReportDomainModel>>>

    fun getUserPendingReports(): Flow<Resource<List<ReportDomainModel>>>

    fun logout()
}