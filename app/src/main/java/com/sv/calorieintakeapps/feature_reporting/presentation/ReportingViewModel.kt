package com.sv.calorieintakeapps.feature_reporting.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportEntity
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.Response
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFoodDetail
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class ReportingViewModel(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ViewModel() {

    private val report = MutableLiveData<Report>()
    private var foodName: String = ""
    private var isSaveToLocalDb: Boolean = false

    fun setFoodDetailId(foodDetailId: Int) {
        this.foodDetailId.value = foodDetailId
    }

    private var foodDetailId = MutableLiveData<Int>()

    val foodDetailResult: LiveData<ApiResponse<UrtFoodDetail>> = foodDetailId.switchMap {
        liveData {
            remoteDataSource.getUrtFoodDetail(it).collectLatest {
                emit(it)
            }
        }
    }

    fun addReport(
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        foodName: String,
        calories: String,
        protein: String,
        fat: String,
        carbs: String,
        air: String,
        gramTotalDikonsumsi: Float,
        isUsingUrt: Boolean,
        gramPerUrt: Float,
        porsiUrt: Int,
        idMakanananNewApi: Int,
        isSavetoLocalDb: Boolean,
    ) {
        val userId = localDataSource.getUserId()
        isSaveToLocalDb = isSavetoLocalDb
        report.value = Report(
            userId = userId,
            date = "$date $time",
            foodName = foodName,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            percentage = percentage,
            mood = mood,
            gramTotalDikonsumsi = gramTotalDikonsumsi,
            isUsingUrt = isUsingUrt,
            gramPerUrt = gramPerUrt,
            porsiUrt = porsiUrt,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            air = air,
            idMakananNewApi = idMakanananNewApi,
        )
    }

    val addReportResult: LiveData<ApiResponse<ReportResponse>> = report.switchMap { report ->
        liveData {
            if (isSaveToLocalDb) {
                val reportEntity = ReportEntity(
                    date = report.date,
                    foodName = report.foodName,
                    percentage = report.percentage,
                    mood = report.mood,
                    gramTotalDikonsumsi = report.gramTotalDikonsumsi,
                    isUsingUrt = report.isUsingUrt,
                    gramPerUrt = report.gramPerUrt,
                    porsiUrt = report.porsiUrt,
                    calories = report.calories,
                    protein = report.protein,
                    carbs = report.carbs,
                    fat = report.fat,
                    air = report.air,
                    idMakanananNewApi = report.idMakananNewApi,
                    preImageFilePath = report.preImageFile?.path ?: "",
                    postImageFilePath = report.postImageFile?.path ?: "",
                )
                flow {
                    val resultFromDb = localDataSource.insertReport(reportEntity)
                    if (resultFromDb > 0) {
                        emit(ApiResponse.Success(ReportResponse()))
                    } else {
                        emit(ApiResponse.Error("Failed to add to db"))
                    }
                }.flowOn(Dispatchers.IO).collectLatest {
                    emit(it)
                }

            } else {
                val result = remoteDataSource.addReport(report)
                result.collectLatest {
                    emit(it)
                }
            }
        }
    }

    fun getReportById(reportId: Int, isFromLocalDb: Boolean): LiveData<ApiResponse<Report>> = liveData {
        if (isFromLocalDb) {
            flow {
                val resultFromDb = localDataSource.getReportById(reportId)
                Log.d("FIKRI res db", reportId.toString())
                if (resultFromDb == null) {
                    emit(ApiResponse.Error("Failed to get from db"))
                    return@flow
                }
                val preImageFile = if (!resultFromDb.preImageFilePath.isNullOrEmpty()) File(resultFromDb.preImageFilePath) else null
                val postImageFile = if (!resultFromDb.postImageFilePath.isNullOrEmpty()) File(resultFromDb.postImageFilePath) else null
                val report = Report(
                    roomId = resultFromDb.roomId,
                    date = resultFromDb.date,
                    percentage = resultFromDb.percentage,
                    mood = resultFromDb.mood,
                    foodName = resultFromDb.foodName,
                    calories = resultFromDb.calories,
                    protein = resultFromDb.protein,
                    fat = resultFromDb.fat,
                    carbs = resultFromDb.carbs,
                    air = resultFromDb.air,
                    gramTotalDikonsumsi = resultFromDb.gramTotalDikonsumsi,
                    isUsingUrt = resultFromDb.isUsingUrt,
                    gramPerUrt = resultFromDb.gramPerUrt,
                    porsiUrt = resultFromDb.porsiUrt,
                    idMakananNewApi = resultFromDb.idMakanananNewApi,
                    preImageFile = preImageFile,
                    postImageFile = postImageFile,
                )
                emit(ApiResponse.Success(report))
            }.flowOn(Dispatchers.IO).collectLatest {
                emit(it)
            }
        } else {
            val userId = localDataSource.getUserId()
            remoteDataSource.getReportById(userId, reportId).collectLatest { response ->
                when(response) {
                    ApiResponse.Empty -> {}
                    is ApiResponse.Error -> {}
                    is ApiResponse.Success -> {
                        val reportResponse = response.data.report
                        val report = Report(
                            id = reportResponse?.id ?: -1,
                            userId = reportResponse?.userId ?: -1,
                            foodName = reportResponse?.foodName ?: "",
                            date = reportResponse?.date ?: "",
                            preImageUrl = reportResponse?.preImage ?: "",
                            postImageUrl = reportResponse?.postImage ?: "",
                            percentage = reportResponse?.percentage,
                            mood = reportResponse?.mood ?: "",
                            carbs = reportResponse?.totalKarbo?.toString() ?: "",
                            protein = reportResponse?.totalProtein?.toString() ?: "",
                            fat = reportResponse?.totalLemak?.toString() ?: "",
                            calories = reportResponse?.totalEnergi?.toString() ?: "",
                            air = reportResponse?.totalAir?.toString() ?: "",
                            gramTotalDikonsumsi = reportResponse?.gramTotalDikonsumsi ?: 0f,
                            isUsingUrt = reportResponse?.isUsingUrt ?: false,
                            gramPerUrt = reportResponse?.gramPerUrt ?: 0f,
                            porsiUrt = reportResponse?.porsiUrt?.toInt() ?: 1,
                            idMakananNewApi = reportResponse?.idMakananNewApi ?: -1,
                        )
                        emit(ApiResponse.Success(report))
                    }
                }
            }
        }
    }
    
    fun editReport(
        reportId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        foodName: String,
        calories: String,
        protein: String,
        fat: String,
        carbs: String,
        air: String,
        gramTotalDikonsumsi: Float,
        isUsingUrt: Boolean,
        gramPerUrt: Float,
        porsiUrt: Int,
        idMakanananNewApi: Int,
    ) {
        val userId = localDataSource.getUserId()
        editReport.value = Report(
            userId = userId,
            id = reportId,
            date = "$date $time",
            foodName = foodName,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            percentage = percentage,
            mood = mood,
            gramTotalDikonsumsi = gramTotalDikonsumsi,
            isUsingUrt = isUsingUrt,
            gramPerUrt = gramPerUrt,
            porsiUrt = porsiUrt,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            air = air,
            idMakananNewApi = idMakanananNewApi
        )
    }

    private val editReport = MutableLiveData<Report>()

    private var isFromLocalDb: Boolean = false

    val editReportResult: LiveData<ApiResponse<Response>> =
        editReport.switchMap {
            liveData {
                val result = remoteDataSource.editReportById(it)
                result.collectLatest {
                    emit(it)
                }
            }
        }
    
    fun deleteReportById(reportId: Int): LiveData<ApiResponse<Response>> = liveData {
        remoteDataSource.deleteReportById(reportId).collectLatest {
            emit(it)
        }
    }

    private val dbToDbReport = MutableLiveData<Report>()

    val dbToDbResult: LiveData<ApiResponse<ReportResponse>> = dbToDbReport.switchMap {
        liveData {
            val reportEntity = ReportEntity(
                roomId = it.roomId ?: -1,
                date = it.date,
                foodName = it.foodName,
                percentage = it.percentage,
                mood = it.mood,
                gramTotalDikonsumsi = it.gramTotalDikonsumsi,
                isUsingUrt = it.isUsingUrt,
                gramPerUrt = it.gramPerUrt,
                porsiUrt = it.porsiUrt,
                calories = it.calories,
                protein = it.protein,
                carbs = it.carbs,
                fat = it.fat,
                air = it.air,
                idMakanananNewApi = it.idMakananNewApi,
                preImageFilePath = it.preImageFile?.path ?: "",
                postImageFilePath = it.postImageFile?.path ?: "",
            )
            flow {
                val result = localDataSource.updateReport(reportEntity)
                if (result > 0) {
                    emit(ApiResponse.Success(ReportResponse()))
                } else {
                    emit(ApiResponse.Error("Failed to add to db"))
                }
            }.flowOn(Dispatchers.IO).collectLatest {
                emit(it)
            }
        }
    }

    fun editFromLocalDbToLocalDb(
        roomId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        foodName: String,
        calories: String,
        protein: String,
        fat: String,
        carbs: String,
        air: String,
        gramTotalDikonsumsi: Float,
        isUsingUrt: Boolean,
        gramPerUrt: Float,
        porsiUrt: Int,
        idMakanananNewApi: Int,
    ) {
        dbToDbReport.value = Report(
            roomId = roomId,
            date = "$date $time",
            foodName = foodName,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            percentage = percentage,
            mood = mood,
            gramTotalDikonsumsi = gramTotalDikonsumsi,
            isUsingUrt = isUsingUrt,
            gramPerUrt = gramPerUrt,
            porsiUrt = porsiUrt,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            air = air,
            idMakananNewApi = idMakanananNewApi
        )
    }

    fun editFromLocalDbToServer(
        roomId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        foodName: String,
        calories: String,
        protein: String,
        fat: String,
        carbs: String,
        air: String,
        gramTotalDikonsumsi: Float,
        isUsingUrt: Boolean,
        gramPerUrt: Float,
        porsiUrt: Int,
        idMakanananNewApi: Int,
    ) {
        val userId = localDataSource.getUserId()
        Log.d("FIKRI", "38263 $roomId")
        reportRoomIdDeletation.value = roomId
        dbToServerReport.value = Report(
            userId = userId,
            date = "$date $time",
            foodName = foodName,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            percentage = percentage,
            mood = mood,
            gramTotalDikonsumsi = gramTotalDikonsumsi,
            isUsingUrt = isUsingUrt,
            gramPerUrt = gramPerUrt,
            porsiUrt = porsiUrt,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            air = air,
            idMakananNewApi = idMakanananNewApi
        )
    }

    fun deleteReportFromLocalDb(roomId: Int) {
        reportRoomIdDeletation.value = roomId
    }

    private val dbToServerReport = MutableLiveData<Report>()

    private var reportRoomIdDeletation = MutableLiveData<Int>()

    val dbDeleteResult: LiveData<Resource<Boolean>> = reportRoomIdDeletation.switchMap { id ->
        liveData {
            flow {
                val resultFromDb = localDataSource.deleteReportById(id)
                if (resultFromDb > 0) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error("Failed to delete"))
                }
            }.flowOn(Dispatchers.IO).collectLatest {
                emit(it)
            }
        }
    }

    val dbToServerReportResult: LiveData<ApiResponse<ReportResponse>> = dbToServerReport.switchMap {
        liveData {
            val result = remoteDataSource.addReport(it)
            result.collectLatest {
                emit(it)
            }
        }
    }
}