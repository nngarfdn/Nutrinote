package com.sv.calorieintakeapps.feature_homepage.domain.repository

import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IHomepageRepository {

    fun isLoggedIn(): Flow<Boolean>

    fun getUserName(): Flow<String>

    fun getUserReports(reportStatus: ReportStatus): Flow<Resource<List<ReportDomainModel>>>

    fun logout()
}