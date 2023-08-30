package com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FoodNutritionSearchResponse(
    
    @Json(name = "data")
    val data: Data? = null,
    
    @Json(name = "message")
    val message: String? = null,
    
    ) {
    
    @JsonClass(generateAdapter = true)
    data class LinksItem(
        
        @Json(name = "active")
        val active: Boolean? = null,
        
        @Json(name = "label")
        val label: String? = null,
        
        @Json(name = "url")
        val url: String? = null,
        
        )
    
    @JsonClass(generateAdapter = true)
    data class Data(
        
        @Json(name = "per_page")
        val perPage: String? = null,
        
        @Json(name = "data")
        val data: List<DataItem?>? = null,
        
        @Json(name = "last_page")
        val lastPage: Int? = null,
        
        @Json(name = "next_page_url")
        val nextPageUrl: String? = null,
        
        @Json(name = "prev_page_url")
        val prevPageUrl: String? = null,
        
        @Json(name = "first_page_url")
        val firstPageUrl: String? = null,
        
        @Json(name = "path")
        val path: String? = null,
        
        @Json(name = "total")
        val total: Int? = null,
        
        @Json(name = "last_page_url")
        val lastPageUrl: String? = null,
        
        @Json(name = "from")
        val from: Int? = null,
        
        @Json(name = "links")
        val links: List<LinksItem?>? = null,
        
        @Json(name = "to")
        val to: Int? = null,
        
        @Json(name = "current_page")
        val currentPage: Int? = null,
        
        )
    
    @JsonClass(generateAdapter = true)
    data class DataItem(
        
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
        
        )
    
}