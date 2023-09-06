package com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NilaigiziComLoginResponse(
    
    @Json(name = "data")
    val data: Data? = null,
    
    @Json(name = "message")
    val message: String? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Data(
        
        @Json(name = "user")
        val user: User? = null,
        
        @Json(name = "token")
        val token: String? = null,
        
        )
    
    @JsonClass(generateAdapter = true)
    data class User(
        
        @Json(name = "whatsapp")
        val whatsapp: String? = null,
        
        @Json(name = "str")
        val str: String? = null,
        
        @Json(name = "profession")
        val profession: String? = null,
        
        @Json(name = "status_description")
        val statusDescription: String? = null,
        
        @Json(name = "gender")
        val gender: String? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "email")
        val email: String? = null,
        
        )
}