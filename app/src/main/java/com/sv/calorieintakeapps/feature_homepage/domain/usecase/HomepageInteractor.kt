package com.sv.calorieintakeapps.feature_homepage.domain.usecase

import com.sv.calorieintakeapps.feature_homepage.domain.repository.IHomepageRepository
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class HomepageInteractor(private val homepageRepository: IHomepageRepository) : HomepageUseCase {

    override fun isLoggedIn(): Flow<Boolean> {
        return homepageRepository.isLoggedIn()
    }

    override fun getUserName(): Flow<String> {
        return homepageRepository.getUserName()
    }

    override fun getUserCompletedReports(): Flow<Resource<List<Report>>> {
        return homepageRepository.getUserReports(ReportStatus.COMPLETE)
    }

    override fun getUserPendingReports(): Flow<Resource<List<Report>>> {
        return homepageRepository.getUserReports(ReportStatus.PENDING)
    }

    override fun logout() {
        homepageRepository.logout()
    }
}