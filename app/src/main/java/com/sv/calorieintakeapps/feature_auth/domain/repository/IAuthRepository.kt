package com.sv.calorieintakeapps.feature_auth.domain.repository

import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<Boolean>>

    fun login(email: String, password: String): Flow<Resource<Boolean>>
}