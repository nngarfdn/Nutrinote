package com.sv.calorieintakeapps.feature_auth.data.repository

import com.sv.calorieintakeapps.feature_auth.domain.repository.IAuthRepository
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.LoginResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.RegisterResponse
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IAuthRepository {

    override fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, RegisterResponse>() {
            private var resultDB = false

            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> {
                return remoteDataSource.register(name, email, password, passwordConfirmation)
            }

            override suspend fun saveCallResult(data: RegisterResponse) {
                resultDB = data.apiStatus == "Created"
            }
        }.asFlow()
    }

    override fun login(email: String, password: String): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, LoginResponse>() {
            private var resultDB = false

            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteDataSource.login(email, password)
            }

            override suspend fun saveCallResult(data: LoginResponse) {
                resultDB = data.apiStatus == 1
                localDataSource.storeLoginSession(
                    userId = data.user?.id ?: -1,
                    userName = data.user?.name.orEmpty()
                )
            }
        }.asFlow()
    }
}