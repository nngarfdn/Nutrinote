package com.sv.calorieintakeapps.feature_merchantlist.data.repository

import com.sv.calorieintakeapps.feature_merchantlist.domain.repository.IMerchantListRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantsResponse
import com.sv.calorieintakeapps.library_database.domain.model.Merchant
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MerchantListRepository(
    private val remoteDataSource: RemoteDataSource
) :
    IMerchantListRepository {

    override fun getAllMerchants(): Flow<Resource<List<Merchant>>> {
        return object : NetworkBoundResource<List<Merchant>, MerchantsResponse>() {
            private var resultDB = listOf<Merchant>()

            override fun loadFromDB(): Flow<List<Merchant>> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: List<Merchant>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<MerchantsResponse>> {
                return remoteDataSource.getAllMerchants()
            }

            override suspend fun saveCallResult(data: MerchantsResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
}