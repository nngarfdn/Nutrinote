package com.sv.calorieintakeapps.feature_merchantmenu.data.repository

import com.sv.calorieintakeapps.feature_merchantmenu.domain.repository.IMerchantMenuRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.response.MerchantMenuResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.response.MerchantsResponse
import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MerchantMenuRepository(
    private val remoteDataSource: RemoteDataSource
) : IMerchantMenuRepository {

    override fun getMerchantNameById(merchantId: Int): Flow<Resource<String>> {
        return object : NetworkBoundResource<String, MerchantsResponse>() {
            private var resultDB = ""

            override fun loadFromDB(): Flow<String> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: String?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<MerchantsResponse>> {
                return remoteDataSource.getAllMerchants()
            }

            override suspend fun saveCallResult(data: MerchantsResponse) {
                resultDB = data.merchants?.find { it?.id == merchantId }?.name.orEmpty()
            }
        }.asFlow()
    }

    override fun getMerchantMenuById(merchantId: Int): Flow<Resource<List<Food>>> {
        return object : NetworkBoundResource<List<Food>, MerchantMenuResponse>() {
            private var resultDB = listOf<Food>()

            override fun loadFromDB(): Flow<List<Food>> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: List<Food>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<MerchantMenuResponse>> {
                return remoteDataSource.getMerchantMenuById(merchantId)
            }

            override suspend fun saveCallResult(data: MerchantMenuResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
}