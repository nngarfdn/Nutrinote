package com.sv.calorieintakeapps.feature_reporting.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IReportingRepository {

    fun addReport(
        foodId: Int,
        date: String,
        time: String,
        percentage: Int,
        mood: String,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>>

    fun getReportById(reportId: Int): Flow<Resource<Report>>

    fun editReportById(
        reportId: Int,
        date: String,
        time: String,
        percentage: Int,
        mood: String,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>>

    fun deleteReportById(reportId: Int): Flow<Resource<Boolean>>
}