package com.sv.calorieintakeapps.library_database.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(

    @Json(name = "api_status")
    val apiStatus: Int? = null,

    @Json(name = "api_message")
    val apiMessage: String? = null,
)