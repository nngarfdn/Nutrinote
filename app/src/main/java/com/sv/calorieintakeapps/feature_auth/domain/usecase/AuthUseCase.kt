package com.sv.calorieintakeapps.feature_auth.domain.usecase

import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<Boolean>>

    fun login(email: String, password: String): Flow<Resource<Boolean>>
    
    fun nilaigiziComLogin(): Flow<Resource<Boolean>>
    
}