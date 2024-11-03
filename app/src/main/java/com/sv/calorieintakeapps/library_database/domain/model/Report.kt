package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import java.io.File

data class Report(
    val id: Int = -1,
    val userId: Int = -1,
    val foodId: Int? = null,
    val foodName: String = "",
    val date: String = "",
    val preImageFile: File? = null,
    val postImageFile: File? = null,
    val status: ReportStatus = ReportStatus.PENDING,
    val percentage: Int? = null,
    val mood: String = "",
    val nilaigiziComFoodId: Int? = null,
    val portionCount: Float? = null,
    val preImageUrl: String = "",
    val postImageUrl: String = "",
    val roomId: Int? = null,
) {
    
    fun getDateOnly(): String {
        return date.split(" ")[0]
    }
    
    fun getTimeOnly(): String {
        return date.split(" ")[1]
    }
    
}