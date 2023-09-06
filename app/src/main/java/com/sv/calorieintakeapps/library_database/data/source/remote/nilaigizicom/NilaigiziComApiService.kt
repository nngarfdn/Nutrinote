package com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom

import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.request.NilaigiziComLoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.NilaigiziComLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NilaigiziComApiService {
    
    @POST("login")
    suspend fun postLogin(
        @Body body: NilaigiziComLoginRequest,
    ): NilaigiziComLoginResponse
    
    @GET("products")
    suspend fun getFoodNutritionSearch(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("sort") sort: String = "prd_nama",
        @Query("order") order: String = "asc",
        @Header("Authorization") token: String,
    ): FoodNutritionSearchResponse
    
    @GET("products/{food_id}")
    suspend fun getFoodNutritionDetails(
        @Path("food_id") foodId: Int,
        @Header("Authorization") token: String,
    ): FoodNutritionDetailsResponse
    
}