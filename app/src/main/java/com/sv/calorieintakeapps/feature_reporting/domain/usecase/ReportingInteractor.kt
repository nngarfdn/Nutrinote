package com.sv.calorieintakeapps.feature_reporting.domain.usecase

import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class ReportingInteractor(private val reportingRepository: IReportingRepository) :
    ReportingUseCase {

    override fun addReport(
        foodId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>> {
        return reportingRepository.addReport(
            foodId = foodId,
            date = date, time = time,
            preImageUri = preImageUri, postImageUri = postImageUri
        )
    }

    override fun getReportById(reportId: Int): Flow<Resource<Report>> {
        return reportingRepository.getReportById(reportId)
    }

    override fun editReportById(
        reportId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>> {
        return reportingRepository.editReportById(
            reportId = reportId,
            date = date, time = time,
            preImageUri = preImageUri, postImageUri = postImageUri
        )
    }

    override fun deleteReportById(reportId: Int): Flow<Resource<Boolean>> {
        return reportingRepository.deleteReportById(reportId)
    }
}