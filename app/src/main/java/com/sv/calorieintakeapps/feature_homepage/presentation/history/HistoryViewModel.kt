//package com.sv.calorieintakeapps.feature_home.presentation.history
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
//import com.sv.calorieintakeapps.feature_homepage.domain.usecase.HomepageUseCase
//import com.sv.calorieintakeapps.library_database.domain.model.Report
//import com.sv.calorieintakeapps.library_database.vo.Resource
//
//class HistoryViewModel(useCase: HomepageUseCase) : ViewModel() {
//
//    val userCompletedReports: LiveData<Resource<List<Report>>> =
//        useCase.getUserCompletedReports().asLiveData()
//
//    val userPendingReports: LiveData<Resource<List<Report>>> =
//        useCase.getUserPendingReports().asLiveData()
//}