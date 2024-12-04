package com.sv.calorieintakeapps.library_database.data.source.remote.urt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UrtFood(
    val id: Int,
    @Json(name = "nama_bahan")
    val name: String
)

