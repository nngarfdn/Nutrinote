package com.sv.calorieintakeapps.library_database.data.source.remote.urt

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

interface UrtApiService {
    
    @GET("all-makanan/{food_name}")
    suspend fun searchFood(
        @Path("food_name") foodName: String,
//        @Header("Authorization") token: String,
    ): List<UrtFood>

    @GET("makanan/{food_id}")
    suspend fun getFoodDetail(
        @Path("food_id") foodId: Int,
//        @Header("Authorization") token: String,
    ): UrtFoodDetail?
}