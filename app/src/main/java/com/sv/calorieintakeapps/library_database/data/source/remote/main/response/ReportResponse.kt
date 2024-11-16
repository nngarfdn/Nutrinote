package com.sv.calorieintakeapps.library_database.data.source.remote.main.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    @Json(name = "data")
    var report: Report? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Report(
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "id_user")
        val userId: Int? = null,
        
        @Json(name = "id_food")
        val foodId: Int? = null,
        
        @Json(name = "nama_makanan")
        val foodName: String? = null,
        
        @Json(name = "status_report")
        val status: String? = null,
        
        @Json(name = "date_report")
        val date: String? = null,
        
        @Json(name = "pre_image")
        val preImage: String? = null,
        
        @Json(name = "post_image")
        val postImage: String? = null,
        
        @Json(name = "percentage")
        val percentage: Int? = null,
        
        @Json(name = "mood")
        val mood: String? = null,
        
        @Json(name = "id_food_nilaigizicom")
        val nilaigiziComFoodId: Int? = null,
        
        @Json(name = "total_portion")
        val portionCount: Float? = null,

        @Json(name = "total_karbohidrat")
        val totalKarbo: Float? = null,

        @Json(name = "total_protein")
        val totalProtein: Float? = null,

        @Json(name = "total_lemak")
        val totalLemak: Float? = null,

        @Json(name = "total_energi")
        val totalEnergi: Float? = null,

        @Json(name = "total_air")
        val totalAir: Float? = null,

        @Json(name = "gram_total_dikonsumsi")
        val gramTotalDikonsumsi: Float? = null,

        @Json(name = "is_menggunakan_urt")
        val isUsingUrtInt: Int? = null,

        var isUsingUrt: Boolean? = null,

        @Json(name = "gram_tiap_urt")
        val gramPerUrt: Float? = null,

        @Json(name = "porsi_urt")
        val porsiUrt: Float? = null,

        @Json(name = "id_makanan_new_api")
        val idMakananNewApi: Int? = null,
        )
    
}