package com.sv.calorieintakeapps.feature_merchantmenu.presentation

import androidx.lifecycle.*
import com.sv.calorieintakeapps.feature_merchantmenu.domain.usecase.MerchantMenuUseCase
import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.vo.Resource

class MerchantMenuViewModel(private val merchantMenuUseCase: MerchantMenuUseCase) : ViewModel() {

    private val merchantId = MutableLiveData<Int>()

    fun setMerchantId(merchantId: Int) {
        this.merchantId.value = merchantId
    }

    val merchantName: LiveData<Resource<String>> =
        merchantId.switchMap {
            merchantMenuUseCase.getMerchantNameById(it).asLiveData()
        }

    val merchantMenu: LiveData<Resource<List<Food>>> =
        merchantId.switchMap {
            merchantMenuUseCase.getMerchantMenuById(it).asLiveData()
        }
}