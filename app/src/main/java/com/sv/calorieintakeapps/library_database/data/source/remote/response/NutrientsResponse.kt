package com.sv.calorieintakeapps.library_database.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NutrientsResponse(

    @Json(name = "api_status")
    val apiStatus: Int? = null,

    @Json(name = "api_message")
    val apiMessage: String? = null,

    @Json(name = "data")
    val nutrients: List<Nutrient?>? = null,
)

@JsonClass(generateAdapter = true)
data class Nutrient(

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "unit")
    val unit: String? = null,
)
