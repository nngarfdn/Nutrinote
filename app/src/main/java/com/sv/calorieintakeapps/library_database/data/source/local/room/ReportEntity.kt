package com.sv.calorieintakeapps.library_database.data.source.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_table")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val roomId: Int = 0,
    val id: Int = 0,
    val userId: Int = -1,
    val foodId: Int? = null,
    val foodName: String = "",
    val date: String = "",
    val preImageFilePath: String? = null, // Store file paths as Strings
    val postImageFilePath: String? = null,
    val percentage: Int? = null,
    val mood: String = "",
    val nilaigiziComFoodId: Int? = null,
    val portionCount: Float? = null,
    val portionSize: String = "",
    val merchantId: Int? = null,
    val calories: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbs: String = "",
)
