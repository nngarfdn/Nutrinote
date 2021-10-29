package com.sv.calorieintakeapps.feature_merchantlist.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.model.Merchant
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MerchantListUseCase {

    fun getAllMerchants(): Flow<Resource<List<Merchant>>>
}