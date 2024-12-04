package com.sv.calorieintakeapps.library_database.data.source.local

import com.sv.calorieintakeapps.library_database.data.source.local.persistence.LoginSessionPreference
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportDao
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportEntity

class LocalDataSource(
    private val loginSessionPreference: LoginSessionPreference,
    private val reportDao: ReportDao
) {

    fun storeLoginSession(userId: Int, userName: String) {
        loginSessionPreference.userId = userId
        loginSessionPreference.userName = userName
    }
    
    fun storeToken(token: String) {
        loginSessionPreference.token = token
    }
    
    fun getUserId(): Int {
        return loginSessionPreference.userId
    }
    
    fun getUserName(): String {
        return loginSessionPreference.userName
    }
    
    fun getToken(): String {
        return loginSessionPreference.token
    }
    
    fun logout() {
        loginSessionPreference.clear()
    }

    suspend fun insertReport(report: ReportEntity): Long {
        return reportDao.insert(report)
    }

    suspend fun updateReport(report: ReportEntity): Int {
        return reportDao.update(report)
    }

    suspend fun getAllReports(): List<ReportEntity> {
        return reportDao.getAllReports()
    }

    suspend fun getReportById(reportId: Int): ReportEntity? {
        return reportDao.getReportById(reportId)
    }

    suspend fun deleteReportById(reportId: Int): Int {
        return reportDao.deleteById(reportId)
    }
}