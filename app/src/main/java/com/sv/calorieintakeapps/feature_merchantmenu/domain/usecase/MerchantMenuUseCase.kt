package com.sv.calorieintakeapps.feature_merchantmenu.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MerchantMenuUseCase {

    fun getMerchantNameById(merchantId: Int): Flow<Resource<String>>

    fun getMerchantMenuById(merchantId: Int): Flow<Resource<List<Food>>>
}