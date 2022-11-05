package com.sv.calorieintakeapps.library_database.data.source.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus

@JsonClass(generateAdapter = true)
data class ReportRequest(

    @Json(name = "id_user")
    val userId: Int,

    @Json(name = "id_food")
    val foodId: Int,

    @Json(name = "date_report")
    val date: String,

    @Json(name = "pre_image")
    val preImage: String,

    @Json(name = "post_image")
    val postImage: String,

    @Json(name = "status_report")
    val status: String = ReportStatus.PENDING.id,

    @Json(name = "percentage")
    val percentage: Int? = null,
)
