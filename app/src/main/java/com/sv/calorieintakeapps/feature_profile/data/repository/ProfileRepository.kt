package com.sv.calorieintakeapps.feature_profile.data.repository

import com.sv.calorieintakeapps.feature_profile.domain.repository.IProfileRepository
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.Response
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.UserResponse
import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.helper.NetworkBoundResource
import com.sv.calorieintakeapps.library_database.helper.UserBuilder
import com.sv.calorieintakeapps.library_database.helper.mapResponseToDomain
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProfileRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IProfileRepository {
    
    override fun getUserProfile(): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            private var resultDB = User()
            
            override fun loadFromDB(): Flow<User> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: User?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                val userId = localDataSource.getUserId()
                return remoteDataSource.getUserProfile(userId)
            }
            
            override suspend fun saveCallResult(data: UserResponse) {
                resultDB = mapResponseToDomain(data)
            }
        }.asFlow()
    }
    
    override fun editUserProfile(
        name: String,
        photoUri: String,
        gender: Gender,
        age: Int,
        password: String,
        height: Int,
        weight: Int,
        activityLevel: ActivityLevel,
        stressLevel: StressLevel,
    ): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, Response>() {
            private var resultDB = false
            private val userId = localDataSource.getUserId()
            
            override fun loadFromDB(): Flow<Boolean> {
                return flowOf(resultDB)
            }
            
            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }
            
            override suspend fun createCall(): Flow<ApiResponse<Response>> {
                val user = UserBuilder.update(
                    id = userId,
                    name = name,
                    photoUri = photoUri,
                    password = password,
                    gender = gender,
                    age = age,
                    height = height,
                    weight = weight,
                    activityLevel = activityLevel,
                    stressLevel = stressLevel,
                )
                return remoteDataSource.editUserProfileById(user)
            }
            
            override suspend fun saveCallResult(data: Response) {
                val isSuccess = data.apiStatus == 1
                resultDB = isSuccess
                if (isSuccess) localDataSource.storeLoginSession(userId, name)
            }
        }.asFlow()
    }
    
}