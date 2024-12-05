package com.sv.calorieintakeapps.feature_homepage.data.repository

import com.sv.calorieintakeapps.feature_homepage.domain.repository.IHomepageRepository
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportsResponse
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomepageRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IHomepageRepository {

    override fun isLoggedIn(): Flow<Boolean> {
        return flowOf(localDataSource.getUserName().isNotEmpty())
    }

    override fun getUserName(): Flow<String> {
        return flowOf(localDataSource.getUserName())
    }

    override fun getUserReports(reportStatus: ReportStatus): Flow<Resource<List<ReportDomainModel>>> {
        return object : NetworkBoundResource<List<ReportDomainModel>, ReportsResponse>() {
            private var resultDB = listOf<ReportDomainModel>()

            override fun loadFromDB(): Flow<List<ReportDomainModel>> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: List<ReportDomainModel>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<ReportsResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getReportsByUserId(userId)
            }

            override suspend fun saveCallResult(data: ReportsResponse) {
                val reports = mapResponseToDomain(data)
                resultDB = reports
            }
        }.asFlow()
    }

    override fun logout() {
        localDataSource.logout()
    }
}