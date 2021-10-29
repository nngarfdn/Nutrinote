package com.sv.calorieintakeapps.feature_reporting.presentation

import androidx.lifecycle.*
import com.sv.calorieintakeapps.feature_reporting.domain.usecase.ReportingUseCase
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.ReportBuilder
import com.sv.calorieintakeapps.library_database.vo.Resource

class ReportingViewModel(private val reportingUseCase: ReportingUseCase) : ViewModel() {

    private val report = MutableLiveData<Report>()

    fun addReport(
        foodId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ) {
        report.value = ReportBuilder.create(
            foodId = foodId, userId = -1,
            date = date, time = time,
            preImageUri = preImageUri, postImageUri = postImageUri
        )
    }

    val addReportResult: LiveData<Resource<Boolean>> =
        Transformations.switchMap(report) {
            reportingUseCase.addReport(
                foodId = it.foodId,
                date = it.getDateOnly(), time = it.getTimeOnly(),
                preImageUri = it.preImage, postImageUri = it.postImage
            ).asLiveData()
        }

    fun getReportById(reportId: Int): LiveData<Resource<Report>> {
        return reportingUseCase.getReportById(reportId).asLiveData()
    }

    fun editReport(
        reportId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ) {
        report.value = ReportBuilder.update(
            id = reportId, userId = -1,
            date = date, time = time,
            preImageUri = preImageUri, postImageUri = postImageUri
        )
    }

    val editReportResult: LiveData<Resource<Boolean>> =
        Transformations.switchMap(report) {
            reportingUseCase.editReportById(
                reportId = it.id,
                date = it.getDateOnly(), time = it.getTimeOnly(),
                preImageUri = it.preImage, postImageUri = it.postImage
            ).asLiveData()
        }

    fun deleteReportById(reportId: Int): LiveData<Resource<Boolean>> {
        return reportingUseCase.deleteReportById(reportId).asLiveData()
    }
}