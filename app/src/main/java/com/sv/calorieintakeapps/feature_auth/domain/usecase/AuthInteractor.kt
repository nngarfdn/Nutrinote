package com.sv.calorieintakeapps.feature_auth.domain.usecase

import com.sv.calorieintakeapps.feature_auth.domain.repository.IAuthRepository
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: IAuthRepository) : AuthUseCase {
    
    override fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
    ): Flow<Resource<Boolean>> {
        return authRepository.register(name, email, password, passwordConfirmation)
    }
    
    override fun login(email: String, password: String): Flow<Resource<Boolean>> {
        return authRepository.login(email, password)
    }
    
    override fun nilaigiziComLogin(): Flow<Resource<Boolean>> {
        return authRepository.nilaigiziComLogin()
    }
    
}