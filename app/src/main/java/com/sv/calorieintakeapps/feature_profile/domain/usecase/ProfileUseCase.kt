package com.sv.calorieintakeapps.feature_profile.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
    
    fun getUserProfile(): Flow<Resource<User>>
    
    fun editUserProfile(
        name: String,
        photoUri: String,
        gender: Gender,
        age: Int,
        password: String,
        height: Int,
        weight: Int,
        activityLevel: ActivityLevel,
        stressLevel: StressLevel,
    ): Flow<Resource<Boolean>>
    
}