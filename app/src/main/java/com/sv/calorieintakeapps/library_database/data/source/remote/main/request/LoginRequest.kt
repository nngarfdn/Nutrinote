package com.sv.calorieintakeapps.library_database.data.source.remote.main.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    
    @Json(name = "email")
    val email: String,
    
    @Json(name = "password")
    val password: String,
    
    )