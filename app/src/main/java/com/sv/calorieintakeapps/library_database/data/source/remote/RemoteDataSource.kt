package com.sv.calorieintakeapps.library_database.data.source.remote

import com.sv.calorieintakeapps.library_database.data.source.remote.network.ApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.request.LoginRequest
import com.sv.calorieintakeapps.library_database.data.source.remote.request.RegisterRequest
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.helper.parseErrorMessage
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val request = RegisterRequest(
                    name = name,
                    email = email,
                    password = password, passwordConfirmation = passwordConfirmation
                )
                val response = apiService.postRegister(request)

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
                val response = apiService.postLogin(request)

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
                val response = apiService.getReportsByUserId(userId)

                val dataArray = response.reports
                if (dataArray?.isNotEmpty() as Boolean) {
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
                val response = apiService.getAllMerchants()

                val dataArray = response.merchants
                if (dataArray?.isNotEmpty() as Boolean) {
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
                val response = apiService.getMerchantMenuById(merchantId)

                val dataArray = response.foods
                if (dataArray?.isNotEmpty() as Boolean) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (throwable: Throwable) {
                emit(ApiResponse.Error(parseErrorMessage(throwable)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFoodNutrientsById(foodId: Int, userId: Int): Flow<ApiResponse<FoodNutrientsResponse>> {
        return flow {
            try {
                val foodNutrientsResponse = apiService.getFoodNutrientsById(foodId, userId)
                val nutrientsResponse = apiService.getAllNutrients()

                val dataArray = foodNutrientsResponse.foodNutrients
                if (dataArray?.isNotEmpty() as Boolean) {
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

    suspend fun addReport(report: Report): Flow<ApiResponse<ReportResponse>> {
        return flow {
            try {
                val contentType = MediaType.parse("multipart")
                val preImageFile = File(report.preImage)
                val postImageFile = File(report.postImage)
                val requestPreImage = RequestBody.create(contentType, preImageFile)
                val requestPostImage = RequestBody.create(contentType, postImageFile)
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
                    val requestPreImage = RequestBody.create(contentType, preImageFile)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        preImageFile.name,
                        requestPreImage
                    )
                }

                if (report.postImage.isNotEmpty()) {
                    val postImageFile = File(report.postImage)
                    val requestPostImage = RequestBody.create(contentType, postImageFile)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        postImageFile.name,
                        requestPostImage
                    )
                }

                val requestBody = multipartBuilder.build()

                val response = apiService.postReport(requestBody)

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
                val response = apiService.getReportById(userId, reportId)

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
                val contentType = MediaType.parse("multipart")

                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("date_report", report.date)
//                    .addFormDataPart("percentage", report.percentage.toString())
                    .addFormDataPart("mood", report.mood)
                if (report.percentage != null) {
                    multipartBuilder.addFormDataPart("percentage", report.percentage.toString())
                }

                if (report.preImage.isNotEmpty()) {
                    val preImageFile = File(report.preImage)
                    val requestPreImage = RequestBody.create(contentType, preImageFile)
                    multipartBuilder.addFormDataPart(
                        "pre_image",
                        preImageFile.name,
                        requestPreImage
                    )
                }

                if (report.postImage.isNotEmpty()) {
                    val postImageFile = File(report.postImage)
                    val requestPostImage = RequestBody.create(contentType, postImageFile)
                    multipartBuilder.addFormDataPart(
                        "post_image",
                        postImageFile.name,
                        requestPostImage
                    )
                }

                val requestBody = multipartBuilder.build()
                val response = apiService.putReportById(report.id, requestBody)

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
                val response = apiService.deleteReportById(reportId)

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
                val response = apiService.getUserProfileById(userId)

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
                val contentType = MediaType.parse("multipart")

                val multipartBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("gender", user.gender.id.toString())

                if (user.name.isNotEmpty()) {
                    multipartBuilder.addFormDataPart("name", user.name)
                }

                if (user.photo.isNotEmpty()) {
                    val photoFile = File(user.photo)
                    val requestPhotoFile = RequestBody.create(contentType, photoFile)
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
                val response = apiService.putUserProfileById(user.id, requestBody)

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
}