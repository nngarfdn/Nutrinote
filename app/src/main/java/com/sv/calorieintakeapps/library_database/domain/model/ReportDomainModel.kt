package com.sv.calorieintakeapps.library_database.domain.model

import java.io.File

data class ReportDomainModel(
    val id: Int = -1,
    val roomId: Int? = null,
    val userId: Int = -1,
    val date: String = "",
    val percentage: Int? = null,
    val mood: String = "",
    val foodName: String = "",
    val gramTotalDikonsumsi: Float = 0f,
    val isUsingUrt: Boolean = false,
    val gramPerUrt: Float = 0f,
    val porsiUrt: Int = 0,
    val preImageUrl: String = "",
    val postImageUrl: String = "",
    val preImageFile: File? = null,
    val postImageFile: File? = null,
    val idMakananNewApi: Int = -1,
    val calories: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbs: String = "",
    val air: String = "",
    //new data
    val urtName: String="",
    val calcium: Float? = 0f,
    val serat: Float? = 0f,
    val abu: Float? = 0f,
    val fosfor: Float? = 0f,
    val besi: Float? = 0f,
    val natrium: Float? = 0f,
    val kalium: Float? = 0f,
    val tembaga: Float? = 0f,
    val seng: Float? = 0f,
    val retinol: Float? = 0f,
    val betaKaroten: Float? = 0f,
    val karotenTotal: Float? = 0f,
    val thiamin: Float? = 0f,
    val rifobla: Float? = 0f,
    val niasin: Float? = 0f,
    val vitaminC: Float? = 0f

) {

    fun getDateOnly(): String {
        return date.split(" ")[0]
    }
    
    fun getTimeOnly(): String {
        return date.split(" ")[1]
    }
    
}