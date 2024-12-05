package com.sv.calorieintakeapps.feature_homepage.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.sv.calorieintakeapps.feature_homepage.domain.usecase.HomepageUseCase
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource
import java.io.File

class HistoryViewModel(useCase: HomepageUseCase, localDataSource: LocalDataSource) : ViewModel() {

    val userCompletedReports: LiveData<Resource<List<ReportDomainModel>>> =
        useCase.getUserCompletedReports().asLiveData()

    val userPendingReports: LiveData<List<ReportDomainModel>> = liveData {
        val list = localDataSource.getAllReports().map { item ->
            ReportDomainModel(
                id = item.id ?: -1,
                foodName = item.foodName,
                date = item.date,
                percentage = item.percentage ?: 0,
                mood = item.mood,
                preImageFile = File(item.preImageFilePath ?: ""),
                postImageFile = File(item.postImageFilePath ?: ""),
                roomId = item.roomId,
                calories = item.calories,
                protein = item.protein,
                fat = item.fat,
                carbs = item.carbs,
                air = item.air,
                gramPerUrt = item.gramPerUrt,
                gramTotalDikonsumsi = item.gramTotalDikonsumsi,
                isUsingUrt = item.isUsingUrt,
                porsiUrt = item.porsiUrt,
                idMakananNewApi = item.idMakanananNewApi
            )
        }.reversed()
        emit(list)
    }
}