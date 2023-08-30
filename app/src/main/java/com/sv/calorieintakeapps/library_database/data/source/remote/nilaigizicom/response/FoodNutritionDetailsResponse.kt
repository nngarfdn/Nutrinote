package com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodNutritionDetailsResponse(
    
    @Json(name = "data")
    val data: Data? = null,
    
    @Json(name = "message")
    val message: String? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class Data(
        
        @Json(name = "mui")
        val mui: Int? = null,
        
        @Json(name = "bpom")
        val bpom: Int? = null,
        
        @Json(name = "images")
        val images: List<String?>? = null,
        
        @Json(name = "bdd")
        val bdd: Int? = null,
        
        @Json(name = "created_by_name_description")
        val createdByNameDescription: String? = null,
        
        @Json(name = "description")
        val description: String? = null,
        
        @Json(name = "type")
        val type: String? = null,
        
        @Json(name = "category_description")
        val categoryDescription: String? = null,
        
        @Json(name = "created_by_description")
        val createdByDescription: String? = null,
        
        @Json(name = "brand_variant_description")
        val brandVariantDescription: String? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "brand_description")
        val brandDescription: String? = null,
        
        @Json(name = "id")
        val id: Int? = null,
        
        @Json(name = "barcode")
        val barcode: String? = null,
        
        @Json(name = "views")
        val views: Int? = null,
        
        @Json(name = "status")
        val status: Int? = null,
        
        @Json(name = "nutritions")
        val nutritions: List<NutritionsItem?>? = null,
        
        )
    
    @JsonClass(generateAdapter = true)
    data class NutritionsItem(
        
        @Json(name = "unit")
        val unit: String? = null,
        
        @Json(name = "name")
        val name: String? = null,
        
        @Json(name = "nutrition_label_reference")
        val nutritionLabelReference: Int? = null,
        
        @Json(name = "priority")
        val priority: Int? = null,
        
        @Json(name = "value")
        val value: Float? = null,
        
        )
    
}