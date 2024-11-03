package com.sv.calorieintakeapps.feature_homepage.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.sv.calorieintakeapps.feature_homepage.domain.usecase.HomepageUseCase
import com.sv.calorieintakeapps.feature_reporting.domain.usecase.ReportingUseCase
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.ReportBuilder
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.asFlow
import java.io.File

class HistoryViewModel(useCase: HomepageUseCase, localDataSource: LocalDataSource) : ViewModel() {

    val userCompletedReports: LiveData<Resource<List<Report>>> =
        useCase.getUserCompletedReports().asLiveData()

    val userPendingReports: LiveData<List<Report>> = liveData {
        val list = localDataSource.getAllReports().map { item ->
            Report(
                id = item.id ?: -1,
                userId = item.userId ?: -1,
                foodId = item.foodId ?: -1,
                foodName = item.foodName.orEmpty(),
                date = item.date.orEmpty(),
                status = ReportStatus.PENDING,
                percentage = item.percentage ?: 0,
                mood = item.mood.orEmpty(),
                nilaigiziComFoodId = item.nilaigiziComFoodId,
                portionCount = item.portionCount,
                preImageFile = File(item.preImageFilePath ?: ""),
                postImageFile = File(item.postImageFilePath ?: ""),
                roomId = item.roomId,
                portionSize = item.portionSize,
                calories = item.calories,
                protein = item.protein,
                fat = item.fat,
                carbs = item.carbs,
            )
        }
        emit(list)
    }
}