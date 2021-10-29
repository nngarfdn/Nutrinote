package com.sv.calorieintakeapps.feature_merchantlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sv.calorieintakeapps.feature_merchantlist.domain.usecase.MerchantListUseCase

class MerchantListViewModel(val useCase: MerchantListUseCase) : ViewModel() {

    val allMerchants = useCase.getAllMerchants().asLiveData()

}