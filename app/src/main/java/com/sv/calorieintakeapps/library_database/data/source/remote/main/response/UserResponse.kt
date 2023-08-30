package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    val user: Profile? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Profile(
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "photo")
        val photo: String? = null,
        
        @Json(name = "email")
        val email: String? = null,
        
        @Json(name = "password")
        val password: String? = null,
        
        @Json(name = "gender")
        val gender: Int? = null,
        
        @Json(name = "age")
        val age: Int? = null,
        
        @Json(name = "height")
        val height: Int? = null,
        
        @Json(name = "weight")
        val weight: Int? = null,
        
        )
    
}