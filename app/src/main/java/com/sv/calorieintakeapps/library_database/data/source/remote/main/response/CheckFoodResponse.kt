package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckFoodResponse(
    
    @Json(name = "data")
    val data: List<DataItem?>? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class DataItem(
        
        @Json(name = "image")
        val image: Any? = null,
        
        @Json(name = "price")
        val price: String? = null,
        
        @Json(name = "is_user_added")
        val isUserAdded: Int? = null,
        
        @Json(name = "porsi")
        val porsi: String? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "id_merchant")
        val idMerchant: Any? = null,
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "label")
        val label: Any? = null,
        
        )
    
}