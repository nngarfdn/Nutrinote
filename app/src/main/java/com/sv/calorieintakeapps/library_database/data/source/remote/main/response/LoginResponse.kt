package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    val user: User? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class User(
        
        @Json(name = "id_user")
        val id: Int? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "email")
        val email: String? = null,
        
        @Json(name = "access_token")
        val accessToken: String? = null,
        
        @Json(name = "expiry")
        val tokenExpiration: Long? = null,
        
        )
    
}