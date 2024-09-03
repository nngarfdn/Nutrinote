package com.sv.calorieintakeapps.library_database.data.source.remote

import com.sv.calorieintakeapps.library_common.util.TAG
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
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.request.NilaigiziComLoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.NilaigiziComLoginResponse
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
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
import timber.log.Timber
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
                    password = password,
                    passwordConfirmation = passwordConfirmation,
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
    
    suspend fun login(
        email: String,
        password: String,
    ): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val request = LoginRequest(
                    email = email,
                    password = password,
                )
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
                val foodNutrientsResponse = mainApiService.getFoodNutrientsById(
                    foodId = foodId,
                    userId = userId,
                )
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
                    mainApiService.getFoodNutrientsById(
                        foodId = foodId,
                        userId = userId,
                    )
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
    
    suspend fun addReport(
        report: Report,
        foodName: String,
        portionSize: String?,
        merchantId: Int?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?,
    ): Flow<ApiResponse<ReportResponse>> {
        // If this food id isn't null, then the selected food is from our database
        var foodId = report.foodId
        
        // If not from our database, it can be from nilaigizi.com or manually added
        // If the proposed food has the same name with food from our database,
        // then we assume it's the same food
        // So, we reassign the food id to the food from our database
        checkFoodDuplication(foodName).let {
            if (it is ApiResponse.Success) {
                foodId = it.data
            } else if (it is ApiResponse.Error) {
                return flow {
                    emit(ApiResponse.Error(it.errorMessage))
                }
            }
        }
        
        // If the food id is still null, then it really is a new food
        // So, we add the food to our database first
        val isManuallyAddedFood = foodId == null && report.nilaigiziComFoodId == null
        if (foodId == null) {
            // We get the food id after adding the food to our database
            addFood(
                foodName = foodName,
                image = report.preImage,
                portionSize = portionSize,
                merchantId = merchantId,
            ).let {
                if (it is ApiResponse.Success) {
                    foodId = it.data
                } else if (it is ApiResponse.Error) {
                    return flow {
                        emit(ApiResponse.Error(it.errorMessage))
                    }
                }
            }
            
            // Note: Manually add food doesn't have nutrition data
            if (calories != null) {
                addFoodNutrition(
                    foodId = foodId,
                    nutritionId = 3,
                    nutritionValue = calories,
                )
                addFoodNutrition(
                    foodId = foodId,
                    nutritionId = 1,
                    nutritionValue = protein,
                )
                addFoodNutrition(
                    foodId = foodId,
                    nutritionId = 4,
                    nutritionValue = fat,
                )
                addFoodNutrition(
                    foodId = foodId,
                    nutritionId = 2,
                    nutritionValue = carbs,
                )
            }
        }
        
        // Only manually added foods need approval
        return addReport(
            foodId = foodId!!,
            userId = report.userId,
            portionCount = report.portionCount,
            date = report.date,
            percentage = report.percentage,
            preImage = report.preImage,
            postImage = report.postImage,
            nilaigiziComFoodId = report.nilaigiziComFoodId,
            status = if (isManuallyAddedFood) ReportStatus.PENDING.id else ReportStatus.COMPLETE.id,
            mood = report.mood,
        )
            .flowOn(Dispatchers.IO)
    }
    
    private suspend fun addReport(
        foodId: Int,
        userId: Int,
        portionCount: Float?,
        date: String,
        percentage: Int?,
        preImage: String,
        postImage: String,
        nilaigiziComFoodId: Int?,
        status: String,
        mood: String,
    ): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val contentType = "multipart".toMediaTypeOrNull()
                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "id_user",
                        userId.toString(),
                    )
                    .addFormDataPart(
                        "date_report",
                        date,
                    )
                    .addFormDataPart(
                        "status_report",
                        status,
                    )
                    .addFormDataPart(
                        "mood",
                        mood,
                    )
                    .addFormDataPart(
                        "id_food",
                        foodId.toString(),
                    )
                if (percentage != null) {
                    multipartBuilder.addFormDataPart(
                        "percentage",
                        percentage.toString(),
                    )
                }
                if (nilaigiziComFoodId != null) {
                    multipartBuilder.addFormDataPart(
                        "id_food_nilaigizicom",
                        nilaigiziComFoodId.toString(),
                    )
                }
                if (portionCount != null) {
                    multipartBuilder.addFormDataPart(
                        "total_portion",
                        portionCount.toString(),
                    )
                }
                
                try {
                    if (preImage.isNotEmpty()) {
                        val preImageFile = File(preImage)
                        val requestPreImage = preImageFile.asRequestBody(contentType)
                        multipartBuilder.addFormDataPart(
                            "pre_image",
                            preImageFile.name,
                            requestPreImage,
                        )
                    }
                    
                    if (postImage.isNotEmpty()) {
                        val postImageFile = File(postImage)
                        val requestPostImage = postImageFile.asRequestBody(contentType)
                        multipartBuilder.addFormDataPart(
                            "post_image",
                            postImageFile.name,
                            requestPostImage,
                        )
                    }
                } catch (e: Exception) {
                    Timber.tag(TAG).e(e)
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
        }
    }
    
    private suspend fun checkFoodDuplication(foodName: String): ApiResponse<Int?> {
        return try {
            val checkFoodResponse = mainApiService.checkFood(foodName)
            if (checkFoodResponse.apiStatus == 1) {
                ApiResponse.Success(checkFoodResponse.data?.firstOrNull()?.id)
            } else {
                ApiResponse.Error(checkFoodResponse.apiMessage.orEmpty())
            }
        } catch (e: Exception) {
            ApiResponse.Error(parseErrorMessage(e))
        }
    }
    
    private suspend fun addFood(
        foodName: String,
        image: String,
        portionSize: String?,
        merchantId: Int?,
    ): ApiResponse<Int?> {
        return try {
            val afMultipartBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "name",
                    foodName,
                )
                .addFormDataPart(
                    "price",
                    "0",
                )
                .addFormDataPart(
                    "porsi",
                    portionSize!!,
                )
                .addFormDataPart(
                    "label",
                    "",
                )
                .addFormDataPart(
                    "is_user_added",
                    "1",
                )
            if (merchantId != null) {
                afMultipartBuilder.addFormDataPart(
                    "id_merchant",
                    merchantId.toString(),
                )
            }
            try {
                if (image.isNotEmpty()) {
                    val imageFile = File(image)
                    val contentType = "multipart".toMediaTypeOrNull()
                    val requestImage = imageFile.asRequestBody(contentType)
                    afMultipartBuilder.addFormDataPart(
                        "image",
                        imageFile.name,
                        requestImage,
                    )
                }
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
            }
            
            val afRequestBody = afMultipartBuilder.build()
            val afResponse = mainApiService.postFood(afRequestBody)
            if (afResponse.apiStatus == 1) {
                ApiResponse.Success(afResponse.data?.id)
            } else {
                ApiResponse.Error(afResponse.apiMessage.orEmpty())
            }
        } catch (e: Exception) {
            ApiResponse.Error(parseErrorMessage(e))
        }
    }
    
    private suspend fun addFoodNutrition(
        foodId: Int?,
        nutritionId: Int,
        nutritionValue: String?,
    ) {
        try {
            val afnMultipartBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "value",
                    nutritionValue.orEmpty(),
                )
                .addFormDataPart(
                    "id_food",
                    foodId.toString(),
                )
                .addFormDataPart(
                    "id_nutrition",
                    nutritionId.toString(),
                )
            val afnRequestBody = afnMultipartBuilder.build()
            mainApiService.postFoodNutrition(afnRequestBody)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    suspend fun getReportById(
        userId: Int,
        reportId: Int,
    ): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val response = mainApiService.getReportById(
                    userId,
                    reportId
                )
                
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
                    .addFormDataPart(
                        "date_report",
                        report.date
                    )
                    .addFormDataPart(
                        "mood",
                        report.mood
                    )
                if (report.foodId != null) {
                    multipartBuilder.addFormDataPart(
                        "id_food",
                        report.foodId.toString()
                    )
                }
                if (report.percentage != null) {
                    multipartBuilder.addFormDataPart(
                        "percentage",
                        report.percentage.toString()
                    )
                }
                if (report.nilaigiziComFoodId != null) {
                    multipartBuilder.addFormDataPart(
                        "id_food_nilaigizicom",
                        report.nilaigiziComFoodId.toString()
                    )
                }
                if (report.portionCount != null) {
                    multipartBuilder.addFormDataPart(
                        "total_portion",
                        report.portionCount.toString()
                    )
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
                val response = mainApiService.putReportById(
                    report.id,
                    requestBody
                )
                
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
                    .addFormDataPart(
                        "gender",
                        user.gender.id.toString()
                    )
                    .addFormDataPart(
                        "activity",
                        user.activityLevel!!.id.toString()
                    )
                    .addFormDataPart(
                        "stress",
                        user.stressLevel!!.id.toString()
                    )
                
                if (user.name.isNotEmpty()) {
                    multipartBuilder.addFormDataPart(
                        "name",
                        user.name
                    )
                }
                
                if (user.photo.isNotEmpty()) {
                    val photoFile = File(user.photo)
                    val requestPhotoFile = photoFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "photo",
                        photoFile.name,
                        requestPhotoFile
                    )
                }
                
                if (user.password.isNotEmpty()) {
                    multipartBuilder.addFormDataPart(
                        "password",
                        user.password
                    )
                }
                
                if (user.age.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart(
                        "age",
                        user.age.toString()
                    )
                }
                
                if (user.height.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart(
                        "height",
                        user.height.toString()
                    )
                }
                if (user.weight.toString().isNotEmpty()) {
                    multipartBuilder.addFormDataPart(
                        "weight",
                        user.weight.toString()
                    )
                }
                
                val requestBody = multipartBuilder.build()
                val response = mainApiService.putUserProfileById(
                    user.id,
                    requestBody
                )
                
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
    
    suspend fun getFoodNutritionSearch(
        query: String,
        token: String,
    ): Flow<ApiResponse<FoodNutritionSearchResponse>> {
        return flow {
            try {
                val response = nilaigiziComApiService.getFoodNutritionSearch(
                    query = query,
                    page = 1,
                    pageSize = 10,
                    token = token,
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
    
    suspend fun getFoodNutritionDetails(
        foodId: Int,
        token: String,
    ): Flow<ApiResponse<FoodNutritionDetailsResponse>> {
        return flow {
            try {
                val response = nilaigiziComApiService.getFoodNutritionDetails(
                    foodId = foodId,
                    token = token,
                )
                
                emit(ApiResponse.Success(response))
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun nilaigiziComLogin(
        email: String,
        password: String,
    ): Flow<ApiResponse<NilaigiziComLoginResponse>> {
        return flow {
            try {
                val request = NilaigiziComLoginRequest(
                    email = email,
                    password = password,
                )
                val response = nilaigiziComApiService.postLogin(request)
                
                emit(ApiResponse.Success(response))
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
    
}