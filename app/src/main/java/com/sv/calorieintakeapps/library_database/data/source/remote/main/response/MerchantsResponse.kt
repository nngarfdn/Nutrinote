package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MerchantsResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    val merchants: List<Merchant?>? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Merchant(
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "address")
        val address: String? = null,
    )
    
}