package com.sv.calorieintakeapps.library_database.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(

    @Json(name = "status")
    val apiStatus: String? = null,

    @Json(name = "message")
    val apiMessage: String? = null,

    @Json(name = "errors")
    val errors: Errors? = null,
)

@JsonClass(generateAdapter = true)
data class Errors(

    @Json(name = "name")
    val name: List<String>? = null,

    @Json(name = "email")
    val email: List<String>? = null,

    @Json(name = "password")
    val password: List<String>? = null,
)