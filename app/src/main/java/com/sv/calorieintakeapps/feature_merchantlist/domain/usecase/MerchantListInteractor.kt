package com.sv.calorieintakeapps.feature_merchantlist.domain.usecase

import com.sv.calorieintakeapps.feature_merchantlist.domain.repository.IMerchantListRepository
import com.sv.calorieintakeapps.library_database.domain.model.Merchant
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class MerchantListInteractor(private val merchantListRepository: IMerchantListRepository) :
    MerchantListUseCase {

    override fun getAllMerchants(): Flow<Resource<List<Merchant>>> {
        return merchantListRepository.getAllMerchants()
    }
}