package com.sv.calorieintakeapps.feature_reporting.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.sv.calorieintakeapps.feature_reporting.domain.usecase.ReportingUseCase
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.ReportBuilder
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import java.io.File

class ReportingViewModel(private val reportingUseCase: ReportingUseCase) : ViewModel() {
    
    private val report = MutableLiveData<Report>()
    private var foodName: String = ""
    private var portionSize: String? = null
    private var merchantId: Int? = null
    
    private var calories: String? = null
    private var protein: String? = null
    private var fat: String? = null
    private var carbs: String? = null
    
    fun addReport(
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
        foodName: String,
        portionSize: String?,
        merchantId: Int?,
        calories: String?,
        protein: String?,
        fat: String?,
        carbs: String?,
    ) {
        this.foodName = foodName
        this.portionSize = portionSize
        this.merchantId = merchantId
        this.calories = calories
        this.protein = protein
        this.carbs = carbs
        this.fat = fat
        report.value = ReportBuilder.create(
            foodId = foodId,
            userId = -1,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
        )
    }

    val addReportResult: LiveData<Resource<Boolean>> = report.switchMap {
        liveData {
            val result = reportingUseCase.addReport(
                foodId = it.foodId,
                date = it.getDateOnly(),
                time = it.getTimeOnly(),
                percentage = it.percentage,
                mood = it.mood,
                preImageFile = it.preImageFile,
                postImageFile = it.postImageFile,
                nilaigiziComFoodId = it.nilaigiziComFoodId,
                portionCount = it.portionCount,
                foodName = foodName,
                portionSize = portionSize,
                merchantId = merchantId,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
            )
            emit(result.single())
        }
    }

    fun getReportById(reportId: Int, isFromLocalDb: Boolean): LiveData<Resource<Report>> = liveData {
        reportingUseCase.getReportById(reportId, isFromLocalDb).collectLatest {
            emit(it)
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
        foodId: Int?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ) {
        report.value = ReportBuilder.update(
            id = reportId, userId = -1,
            date = date,
            time = time,
            percentage = percentage,
            mood = mood,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
            foodId = foodId,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
        )
    }
    
    val editReportResult: LiveData<Resource<Boolean>> =
        report.switchMap {
            reportingUseCase.editReportById(
                reportId = it.id,
                date = it.getDateOnly(),
                time = it.getTimeOnly(),
                percentage = it.percentage,
                mood = it.mood,
                preImageFile = it.preImageFile,
                postImageFile = it.postImageFile,
                foodId = it.foodId,
                nilaigiziComFoodId = it.nilaigiziComFoodId,
                portionCount = it.portionCount,
            ).asLiveData()
        }
    
    fun deleteReportById(reportId: Int): LiveData<Resource<Boolean>> {
        return reportingUseCase.deleteReportById(reportId).asLiveData()
    }
    
}