package com.sv.calorieintakeapps.library_database.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MerchantMenuResponse(

    @Json(name = "api_status")
    val apiStatus: Int? = null,

    @Json(name = "api_message")
    val apiMessage: String? = null,

    @Json(name = "data")
    val foods: List<Food?>? = null,
)

@JsonClass(generateAdapter = true)
data class Food(

    @Json(name = "id_merchant")
    val merchantId: Int? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "price")
    val price: String? = null,

    @Json(name = "porsi")
    val portion: Int? = null,

    @Json(name = "label")
    val label: String? = null,

    @Json(name = "image")
    val image: String? = null,
)