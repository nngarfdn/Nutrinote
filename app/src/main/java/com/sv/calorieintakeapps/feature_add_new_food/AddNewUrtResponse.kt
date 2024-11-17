package com.sv.calorieintakeapps.feature_add_new_food

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddNewUrtResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "createdId")
    val createdId: Int,
)
