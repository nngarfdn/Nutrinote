package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import java.io.File

data class Report(
    val id: Int = -1,
    val roomId: Int? = null,
    val userId: Int = -1,
    val date: String = "",
    val percentage: Int? = null,
    val mood: String = "",
    val foodName: String = "",
    val calories: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbs: String = "",
    val air: String = "",
    val gramTotalDikonsumsi: Float = 0f,
    val isUsingUrt: Boolean = false,
    val gramPerUrt: Float = 0f,
    val porsiUrt: Int = 0,
    val preImageUrl: String = "",
    val postImageUrl: String = "",
    val preImageFile: File? = null,
    val postImageFile: File? = null,
    val idMakananNewApi: Int = -1,
) {

    fun getDateOnly(): String {
        return date.split(" ")[0]
    }
    
    fun getTimeOnly(): String {
        return date.split(" ")[1]
    }
    
}