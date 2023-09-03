package com.sv.calorieintakeapps.library_database.data.source.remote

import com.sv.calorieintakeapps.library_database.data.source.remote.main.MainApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.main.request.LoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.main.request.RegisterRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.LoginResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantMenuResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.RegisterResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.Response
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.UserResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.NilaigiziComApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.helper.parseErrorMessage
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RemoteDataSource(
    private val mainApiService: MainApiService,
    private val nilaigiziComApiService: NilaigiziComApiService,
) {
    
    suspend fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
    ): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val request = RegisterRequest(
                    name = name,
                    email = email,
                    password = password, passwordConfirmation = passwordConfirmation
                )
                val response = mainApiService.postRegister(request)
                
                if (response.apiStatus == "Created") {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(
                        ApiResponse.Error(response.apiMessage.orEmpty())
                    )
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val request = LoginRequest(email, password)
                val response = mainApiService.postLogin(request)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getReportsByUserId(userId: Int): Flow<ApiResponse<ReportsResponse>> {
        return flow {
            try {
                val response = mainApiService.getReportsByUserId(userId)
                
                val dataArray = response.reports
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getAllMerchants(): Flow<ApiResponse<MerchantsResponse>> {
        return flow {
            try {
                val response = mainApiService.getAllMerchants()
                
                val dataArray = response.merchants
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getMerchantMenuById(merchantId: Int): Flow<ApiResponse<MerchantMenuResponse>> {
        return flow {
            try {
                val response = mainApiService.getMerchantMenuById(merchantId)
                
                val dataArray = response.foods
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getFoodNutrientsById(
        foodId: Int,
        userId: Int,
    ): Flow<ApiResponse<FoodNutrientsResponse>> {
        return flow {
            try {
                val foodNutrientsResponse = mainApiService.getFoodNutrientsById(foodId, userId)
                val nutrientsResponse = mainApiService.getAllNutrients()
                
                val dataArray = foodNutrientsResponse.foodNutrients
                if (dataArray?.isNotEmpty() == true) {
                    dataArray.forEach { foodNutrient ->
                        val relatedNutrient = nutrientsResponse.nutrients?.find {
                            foodNutrient?.nutrientId == it?.id
                        }
                        foodNutrient?.nutrientName = relatedNutrient?.name
                        foodNutrient?.nutrientUnit = relatedNutrient?.unit
                    }
                    emit(ApiResponse.Success(foodNutrientsResponse))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getFoodNutrientsByFoodIds(
        foodIds: List<Int>,
        userId: Int,
    ): Flow<ApiResponse<List<FoodNutrientsResponse>>> {
        return flow {
            try {
                val foodNutrientsResponses = foodIds.map { foodId ->
                    mainApiService.getFoodNutrientsById(foodId, userId)
                }
                val nutrientsResponse = mainApiService.getAllNutrients()
                
                val foodNutrientsResponsesFinal = mutableListOf<FoodNutrientsResponse>()
                foodNutrientsResponses.forEach { foodNutrientsResponse ->
                    if (foodNutrientsResponse.foodNutrients?.isNotEmpty() == true) {
                        foodNutrientsResponse.foodNutrients.forEach { foodNutrient ->
                            val relatedNutrient = nutrientsResponse.nutrients?.find {
                                foodNutrient?.nutrientId == it?.id
                            }
                            foodNutrient?.nutrientName = relatedNutrient?.name
                            foodNutrient?.nutrientUnit = relatedNutrient?.unit
                        }
                        foodNutrientsResponsesFinal.add(foodNutrientsResponse)
                    }
                }
                
                if (foodNutrientsResponsesFinal.isNotEmpty()) {
                    emit(ApiResponse.Success(foodNutrientsResponsesFinal))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun addReport(report: Report): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val contentType = "multipart".toMediaTypeOrNull()
                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id_user", report.userId.toString())
                    .addFormDataPart("id_food", report.foodId.toString())
                    .addFormDataPart("date_report", report.date)
                    .addFormDataPart("status_report", report.status.id)
                    .addFormDataPart("mood", report.mood)
                if (report.percentage != null) {
                    multipartBuilder.addFormDataPart("percentage", report.percentage.toString())
                }
                
                if (report.preImage.isNotEmpty()) {
                    val preImageFile = File(report.preImage)
                    val requestPreImage = preImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        preImageFile.name,
                        requestPreImage
                    )
                }
                
                if (report.postImage.isNotEmpty()) {
                    val postImageFile = File(report.postImage)
                    val requestPostImage = postImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        postImageFile.name,
                        requestPostImage
                    )
                }
                
                val requestBody = multipartBuilder.build()
                
                val response = mainApiService.postReport(requestBody)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getReportById(userId: Int, reportId: Int): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val response = mainApiService.getReportById(userId, reportId)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun editReportById(report: Report): Flow<ApiResponse<Response>> {
        return flow {
            try {
                val contentType = "multipart".toMediaTypeOrNull()
                
                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("date_report", report.date)
                    .addFormDataPart("mood", report.mood)
                if (report.percentage != null) {
                    multipartBuilder.addFormDataPart("percentage", report.percentage.toString())
                }
                
                if (report.preImage.isNotEmpty()) {
                    val preImageFile = File(report.preImage)
                    val requestPreImage = preImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        preImageFile.name,
                        requestPreImage
                    )
                }
                
                if (report.postImage.isNotEmpty()) {
                    val postImageFile = File(report.postImage)
                    val requestPostImage = postImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        postImageFile.name,
                        requestPostImage
                    )
                }
                
                val requestBody = multipartBuilder.build()
                val response = mainApiService.putReportById(report.id, requestBody)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun deleteReportById(reportId: Int): Flow<ApiResponse<Response>> {
        return flow {
            try {
                val response = mainApiService.deleteReportById(reportId)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getUserProfile(userId: Int): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = mainApiService.getUserProfileById(userId)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun editUserProfileById(user: User): Flow<ApiResponse<Response>> {
        return flow {
            try {
                val contentType = "multipart".toMediaTypeOrNull()
                
                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("gender", user.gender.id.toString())
                
                if (user.name.isNotEmpty()) {
                    multipartBuilder.addFormDataPart("name", user.name)
                }
                
                if (user.photo.isNotEmpty()) {
                    val photoFile = File(user.photo)
                    val requestPhotoFile = photoFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart("photo", photoFile.name, requestPhotoFile)
                }
                
                if (user.password.isNotEmpty()) {
                    multipartBuilder.addFormDataPart("password", user.password)
                }
                
                if (user.age.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart("age", user.age.toString())
                }
                
                if (user.height.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart("height", user.height.toString())
                }
                if (user.weight.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart("weight", user.weight.toString())
                }
                
                val requestBody = multipartBuilder.build()
                val response = mainApiService.putUserProfileById(user.id, requestBody)
                
                if (response.apiStatus == 1) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.apiMessage.orEmpty()))
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getFoodNutritionSearch(query: String): Flow<ApiResponse<FoodNutritionSearchResponse>> {
        return flow {
            try {
                val response = nilaigiziComApiService.getFoodNutritionSearch(
                    query = query,
                    page = 1,
                    pageSize = 10,
                )
                
                val dataArray = response.data?.data
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getFoodNutritionDetails(foodId: Int): Flow<ApiResponse<FoodNutritionDetailsResponse>> {
        return flow {
            try {
                val response = nilaigiziComApiService.getFoodNutritionDetails(
                    foodId = foodId,
                )
                
                emit(ApiResponse.Success(response))
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
}