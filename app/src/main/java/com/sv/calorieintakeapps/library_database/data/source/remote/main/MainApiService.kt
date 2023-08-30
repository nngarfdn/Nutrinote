package com.sv.calorieintakeapps.library_database.data.source.remote.main

import com.sv.calorieintakeapps.BuildConfig.GIZI_SECRET_KEY
import com.sv.calorieintakeapps.library_database.data.source.remote.main.request.LoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.main.request.RegisterRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.LoginResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantMenuResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.NutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.RegisterResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.Response
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.UserResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface MainApiService {
    
    @POST("admin/register")
    @Headers("Accept: application/json")
    suspend fun postRegister(
        @Body body: RegisterRequest,
    ): RegisterResponse
    
    @POST("get-token?secret=$GIZI_SECRET_KEY")
    suspend fun postLogin(
        @Body body: LoginRequest,
    ): LoginResponse
    
    @GET("get_list_reports")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getReportsByUserId(
        @Query("id_user") userId: Int,
    ): ReportsResponse
    
    @GET("get_merchants")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getAllMerchants(): MerchantsResponse
    
    @GET("get_foods")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getMerchantMenuById(
        @Query("id_merchant") merchantId: Int,
    ): MerchantMenuResponse
    
    @GET("get_detail_food")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getFoodNutrientsById(
        @Query("id_food") foodId: Int,
        @Query("id_user") userId: Int,
    ): FoodNutrientsResponse
    
    @GET("get_nutritions")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getAllNutrients(): NutrientsResponse
    
    @POST("create_report")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun postReport(
        @Body body: RequestBody,
    ): ReportResponse
    
    @GET("get_detail_report")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getReportById(
        @Query("id_user") userId: Int,
        @Query("id") reportId: Int,
    ): ReportResponse
    
    @POST("edit_report")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun putReportById(
        @Query("id") reportId: Int,
        @Body body: RequestBody,
    ): Response
    
    @GET("delete_report")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun deleteReportById(
        @Query("id") reportId: Int,
    ): Response
    
    @GET("get_profile")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun getUserProfileById(
        @Query("id") userId: Int,
    ): UserResponse
    
    @POST("edit_profile")
    @Headers("API_Key: $GIZI_SECRET_KEY")
    suspend fun putUserProfileById(
        @Query("id") userId: Int,
        @Body body: RequestBody,
    ): Response
    
}