package com.sv.calorieintakeapps.library_database.data.source.remote

import com.sv.calorieintakeapps.feature_add_new_food.NewFood
import com.sv.calorieintakeapps.feature_add_new_food.NewUrt
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
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFood
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFoodDetail
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.helper.parseErrorMessage
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RemoteDataSource(
    private val mainApiService: MainApiService,
    private val nilaigiziComApiService: NilaigiziComApiService,
    private val urtApiService: UrtApiService,
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
                val response = mainApiService.getReportsByUserId(userId).apply {
                    reports = reports?.map {
                        val bol = it?.isUsingUrtInt == 1
                        it?.copy(isUsingUrt = bol)
                    }
                }

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

    suspend fun addNewFood(newFood: NewFood): Flow<ApiResponse<String>> {
        return flow {
            try {
                urtApiService.addNewFood(
                    foodName = newFood.name,
                    carbs = newFood.carbs,
                    fat = newFood.fat,
                    calories = newFood.calories,
                    protein = newFood.protein,
                    water = newFood.water,
                    urtTersedia = newFood.urtTersedia,
                )
                emit(ApiResponse.Success(""))
            } catch (e: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(e)))
            }
        }
    }

    suspend fun addNewUrt(listNewUrt: List<NewUrt>): Flow<ApiResponse<List<String>>> {
        return flow {
            try {
                val ids = listNewUrt.map { urtApiService.addNewUrt(it)?.createdId.toString() }
                emit(ApiResponse.Success(ids))
            } catch (e: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(e)))
            }
        }
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

    fun addReport(report: ReportDomainModel): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val contentType = "multipart".toMediaTypeOrNull()
                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id_user", report.userId.toString())
                    .addFormDataPart("date_report", report.date)
                    .addFormDataPart("status_report", ReportStatus.COMPLETE.id)
                    .addFormDataPart("mood", report.mood)
                if (report.percentage != null) {
                    multipartBuilder.addFormDataPart("percentage", report.percentage.toString())
                }
                multipartBuilder.addFormDataPart(
                    "gram_total_dikonsumsi",
                    report.gramTotalDikonsumsi.toString()
                )
                multipartBuilder.addFormDataPart(
                    "nama_makanan",
                    report.foodName
                )
                multipartBuilder.addFormDataPart(
                    "is_menggunakan_urt",
                    if (report.isUsingUrt) "1" else "0"
                )
                multipartBuilder.addFormDataPart(
                    "gram_tiap_urt",
                    report.gramPerUrt.toString()
                )
                multipartBuilder.addFormDataPart(
                    "porsi_urt",
                    report.porsiUrt.toString()
                )
                multipartBuilder.addFormDataPart(
                    "total_karbohidrat",
                    report.carbs
                )
                multipartBuilder.addFormDataPart(
                    "total_protein",
                    report.protein
                )
                multipartBuilder.addFormDataPart(
                    "total_lemak",
                    report.fat
                )
                multipartBuilder.addFormDataPart(
                    "total_energi",
                    report.calories
                )
                multipartBuilder.addFormDataPart(
                    "total_air",
                    report.air
                )
                multipartBuilder.addFormDataPart(
                    "id_makanan_new_api",
                    report.idMakananNewApi.toString()
                )
                multipartBuilder.addFormDataPart(
                    "kalsium_total",
                    report.calcium.toString()
                )
                multipartBuilder.addFormDataPart(
                    "serat_total",
                    report.serat.toString()
                )
                multipartBuilder.addFormDataPart(
                    "abu_total",
                    report.abu.toString()
                )
                multipartBuilder.addFormDataPart(
                    "fosfor_total",
                    report.fosfor.toString()
                )
                multipartBuilder.addFormDataPart(
                    "besi_total",
                    report.besi.toString()
                )
                multipartBuilder.addFormDataPart(
                    "natrium_total",
                    report.natrium.toString()
                )
                multipartBuilder.addFormDataPart(
                    "kalium_total",
                    report.kalium.toString()
                )
                multipartBuilder.addFormDataPart(
                    "tembaga_total",
                    report.tembaga.toString()
                )
                multipartBuilder.addFormDataPart(
                    "seng_total",
                    report.seng.toString()
                )
                multipartBuilder.addFormDataPart(
                    "retinol_total",
                    report.retinol.toString()
                )
                multipartBuilder.addFormDataPart(
                    "beta_karoten_total",
                    report.betaKaroten.toString()
                )
                multipartBuilder.addFormDataPart(
                    "karoten_total_total",
                    report.karotenTotal.toString()
                )
                multipartBuilder.addFormDataPart(
                    "thiamin_total",
                    report.thiamin.toString()
                )
                multipartBuilder.addFormDataPart(
                    "rifobla_total",
                    report.rifobla.toString()
                )
                multipartBuilder.addFormDataPart(
                    "niasin_total",
                    report.niasin.toString()
                )
                multipartBuilder.addFormDataPart(
                    "vit_c_total",
                    report.vitaminC.toString()
                )
                if (report.isUsingUrt){
                    multipartBuilder.addFormDataPart(
                        "nama_urt",
                        report.urtName
                    )
                }
                if (report.preImageFile != null) {
                    val requestPreImage = report.preImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        report.preImageFile.name,
                        requestPreImage
                    )
                }

                if (report.postImageFile != null) {
                    val requestPostImage = report.postImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        report.postImageFile.name,
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

    private suspend fun FlowCollector<ApiResponse<ReportResponse>>.addFoodNutrition(
        foodId: Int?,
        nutritionId: Int,
        nutritionValue: String?,
    ) {
        val afnMultipartBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("value", nutritionValue.orEmpty())
            .addFormDataPart("id_food", foodId.toString())
            .addFormDataPart("id_nutrition", nutritionId.toString())
        val afnRequestBody = afnMultipartBuilder.build()
        val afnResponse = mainApiService.postFoodNutrition(afnRequestBody)
        if (afnResponse.apiStatus != 1) {
            emit(ApiResponse.Error(afnResponse.apiMessage.orEmpty()))
        }
    }

    suspend fun getReportById(userId: Int, reportId: Int): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val response = mainApiService.getReportById(reportId)
                val bol = response.report?.isUsingUrtInt == 1
                response.report = response.report?.copy(isUsingUrt = bol)

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

    suspend fun editReportById(report: ReportDomainModel): Flow<ApiResponse<Response>> {
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
                multipartBuilder.addFormDataPart(
                    "gram_total_dikonsumsi",
                    report.gramTotalDikonsumsi.toString()
                )
                multipartBuilder.addFormDataPart(
                    "is_menggunakan_urt",
                    if (report.isUsingUrt) "1" else "0"
                )
                multipartBuilder.addFormDataPart(
                    "gram_tiap_urt",
                    report.gramPerUrt.toString()
                )
                multipartBuilder.addFormDataPart(
                    "porsi_urt",
                    report.porsiUrt.toString()
                )
                multipartBuilder.addFormDataPart(
                    "total_karbohidrat",
                    report.carbs
                )
                multipartBuilder.addFormDataPart(
                    "total_protein",
                    report.protein
                )
                multipartBuilder.addFormDataPart(
                    "total_lemak",
                    report.fat
                )
                multipartBuilder.addFormDataPart(
                    "total_energi",
                    report.calories
                )
                multipartBuilder.addFormDataPart(
                    "total_air",
                    report.air
                )
                if (report.preImageFile != null) {
                    val requestPreImage = report.preImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        report.preImageFile.name,
                        requestPreImage
                    )
                }

                if (report.postImageFile != null) {
                    val requestPostImage = report.postImageFile.asRequestBody(contentType)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        report.postImageFile.name,
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
                    .addFormDataPart("activity", user.activityLevel!!.id.toString())
                    .addFormDataPart("stress", user.stressLevel!!.id.toString())

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
                val request = NilaigiziComLoginRequest(email, password)
                val response = nilaigiziComApiService.postLogin(request)

                emit(ApiResponse.Success(response))
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchUrtFood(
        foodName: String,
    ): Flow<ApiResponse<List<UrtFood>>> {
        return flow {
            try {
                val responses = urtApiService.searchFood(foodName)
                if (responses.isNotEmpty()) {
                    emit(ApiResponse.Success(responses))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUrtFoodDetail(
        foodId: Int,
    ): Flow<ApiResponse<UrtFoodDetail>> {
        return flow {
            try {
                val response = urtApiService.getFoodDetail(foodId)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }
}