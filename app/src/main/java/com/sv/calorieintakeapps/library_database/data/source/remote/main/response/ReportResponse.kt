package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    val report: Report? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Report(
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "id_user")
        val userId: Int? = null,
        
        @Json(name = "id_food")
        val foodId: Int? = null,
        
        @Json(name = "food_name")
        val foodName: String? = null,
        
        @Json(name = "status_report")
        val status: String? = null,
        
        @Json(name = "date_report")
        val date: String? = null,
        
        @Json(name = "pre_image")
        val preImage: String? = null,
        
        @Json(name = "post_image")
        val postImage: String? = null,
        
        @Json(name = "percentage")
        val percentage: Int? = null,
        
        @Json(name = "mood")
        val mood: String? = null,
        
        @Json(name = "id_food_nilaigizicom")
        val nilaigiziComFoodId: Int? = null,
        
        @Json(name = "total_portion")
        val portionCount: Float? = null,
        
        )
    
}