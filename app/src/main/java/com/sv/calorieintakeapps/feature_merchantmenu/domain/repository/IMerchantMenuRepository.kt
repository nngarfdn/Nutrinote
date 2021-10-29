package com.sv.calorieintakeapps.feature_merchantmenu.domain.repository

import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IMerchantMenuRepository {

    fun getMerchantNameById(merchantId: Int): Flow<Resource<String>>

    fun getMerchantMenuById(merchantId: Int): Flow<Resource<List<Food>>>
}