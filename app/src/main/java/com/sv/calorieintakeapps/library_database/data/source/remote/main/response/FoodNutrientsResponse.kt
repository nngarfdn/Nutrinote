package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodNutrientsResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    val foodNutrients: List<FoodNutrient?>? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class FoodNutrient(
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "id_food")
        val foodId: Int? = null,
        
        @Json(name = "id_nutrition")
        val nutrientId: Int? = null,
        
        var nutrientName: String? = null,
        
        var nutrientUnit: String? = null,
        
        @Json(name = "value")
        val value: Double? = null,
        
        @Json(name = "akg_day")
        val akgDay: String? = null,
        
        )
    
}