package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus

data class Report(
    val id: Int = -1,
    val userId: Int = -1,
    val foodId: Int? = null,
    val foodName: String = "",
    val date: String = "",
    val preImage: String = "",
    val postImage: String = "",
    val status: ReportStatus = ReportStatus.PENDING,
    val percentage: Int? = null,
    val mood: String = "",
    val nilaigiziComFoodId: Int? = null,
    val portionCount: Float? = null,
) {
    
    fun getDateOnly(): String {
        return date.split(" ")[0]
    }
    
    fun getTimeOnly(): String {
        return date.split(" ")[1]
    }
    
}