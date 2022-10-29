package com.sv.calorieintakeapps.feature_reporting.data.repository

import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.response.Response
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.ReportBuilder
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReportingRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IReportingRepository {

    override fun addReport(
        foodId: Int,
        date: String,
        time: String,
        percentage: Int,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, ReportResponse>() {
            private var resultDB = false

            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<ReportResponse>> {
                val userId = localDataSource.getUserId()
                val report = ReportBuilder.create(
                    userId = userId, foodId = foodId,
                    date = date, time = time,
                    percentage = percentage,
                    preImageUri = preImageUri, postImageUri = postImageUri
                )
                return remoteDataSource.addReport(report)
            }

            override suspend fun saveCallResult(data: ReportResponse) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }

    override fun getReportById(reportId: Int): Flow<Resource<Report>> {
        return object : NetworkBoundResource<Report, ReportResponse>() {
            private var resultDB = Report()

            override fun loadFromDB(): Flow<Report> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Report?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<ReportResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getReportById(userId, reportId)
            }

            override suspend fun saveCallResult(data: ReportResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }

    override fun editReportById(
        reportId: Int,
        date: String,
        time: String,
        percentage: Int,
        preImageUri: String,
        postImageUri: String
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, Response>() {
            private var resultDB = false

            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<Response>> {
                val userId = localDataSource.getUserId()
                val report = ReportBuilder.update(
                    id = reportId, userId = userId,
                    date = date, time = time,
                    percentage = percentage,
                    preImageUri = preImageUri, postImageUri = postImageUri
                )
                return remoteDataSource.editReportById(report)
            }

            override suspend fun saveCallResult(data: Response) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }

    override fun deleteReportById(reportId: Int): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, Response>() {
            private var resultDB = false

            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<Response>> {
                return remoteDataSource.deleteReportById(reportId)
            }

            override suspend fun saveCallResult(data: Response) {
                resultDB = data.apiStatus == 1
            }
        }.asFlow()
    }
}