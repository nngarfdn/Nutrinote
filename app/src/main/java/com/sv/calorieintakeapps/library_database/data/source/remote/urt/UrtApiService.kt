package com.sv.calorieintakeapps.library_database.data.source.remote.urt

import com.sv.calorieintakeapps.feature_add_new_food.AddNewFoodResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.request.NilaigiziComLoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.NilaigiziComLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @FormUrlEncoded
    @POST("makanan")
    suspend fun addNewFood(
        @Field("nama_bahan") foodName: String,
        @Field("karbohidrat") carbs: String,
        @Field("lemak") fat: String,
        @Field("protein") protein: String,
        @Field("energi") calories: String,
        @Field("air") water: String,
    ): AddNewFoodResponse?
}