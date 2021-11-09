package com.sv.calorieintakeapps.feature_profile.domain.repository

import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    fun getUserProfile(): Flow<Resource<User>>

    fun editUserProfile(
        name: String,
        photoUri: String,
        gender: Gender,
        age: Int,
        password: String,
        height: Int,
        weight: Int
    ): Flow<Resource<Boolean>>
}