package com.sv.calorieintakeapps.library_database.data.source.local.room

import androidx.room.*

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ReportEntity): Long

    @Update
    suspend fun update(report: ReportEntity)

    @Delete
    suspend fun delete(report: ReportEntity)

    @Query("SELECT * FROM report_table WHERE id = :id")
    suspend fun getReportById(id: Int): ReportEntity?

    @Query("SELECT * FROM report_table")
    suspend fun getAllReports(): List<ReportEntity>
}
