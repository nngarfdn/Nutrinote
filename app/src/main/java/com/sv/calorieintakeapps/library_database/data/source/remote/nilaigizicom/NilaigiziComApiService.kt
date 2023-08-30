package com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom

import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_TOKEN
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NilaigiziComApiService {
    
    @GET("products")
    @Headers(
        "Authorization: Bearer $NILAIGIZI_COM_TOKEN",
    )
    suspend fun getFoodNutritionSearch(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("sort") sort: String = "prd_nama",
        @Query("order") order: String = "asc",
    ): FoodNutritionSearchResponse
    
    @GET("products/{food_id}")
    @Headers("Authorization: Bearer $NILAIGIZI_COM_TOKEN")
    suspend fun getFoodNutritionDetails(
        @Path("food_id") foodId: Int,
    ): FoodNutritionDetailsResponse
    
}