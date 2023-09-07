package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostFoodResponse(
    
    @Json(name = "data")
    val data: Data? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    ) {
    
    
    @JsonClass(generateAdapter = true)
    data class Data(
        
        @Json(name = "id")
        val id: Int? = null,
        
        )
    
}