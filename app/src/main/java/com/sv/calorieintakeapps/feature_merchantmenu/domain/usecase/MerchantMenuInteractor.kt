package com.sv.calorieintakeapps.feature_merchantmenu.domain.usecase

import com.sv.calorieintakeapps.feature_merchantmenu.domain.repository.IMerchantMenuRepository
import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class MerchantMenuInteractor(private val merchantMenuRepository: IMerchantMenuRepository) :
    MerchantMenuUseCase {

    override fun getMerchantNameById(merchantId: Int): Flow<Resource<String>> {
        return merchantMenuRepository.getMerchantNameById(merchantId)
    }

    override fun getMerchantMenuById(merchantId: Int): Flow<Resource<List<Food>>> {
        return merchantMenuRepository.getMerchantMenuById(merchantId)
    }
}