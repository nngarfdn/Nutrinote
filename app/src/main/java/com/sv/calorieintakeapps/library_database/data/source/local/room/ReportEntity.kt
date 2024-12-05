package com.sv.calorieintakeapps.library_database.data.source.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_table")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val roomId: Int = 0,
    val id: Int = 0,
    val date: String = "",
    val percentage: Int? = null,
    val mood: String,
    val foodName: String,
    val calories: String,
    val protein: String,
    val fat: String,
    val carbs: String,
    val air: String,
    val calcium: Float? = null,
    val gramTotalDikonsumsi: Float,
    val isUsingUrt: Boolean,
    val gramPerUrt: Float,
    val porsiUrt: Int,
    val idMakanananNewApi: Int,
    val preImageFilePath: String? = null,
    val postImageFilePath: String? = null,
)
