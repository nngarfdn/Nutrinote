package com.sv.calorieintakeapps.feature_profile.domain.usecase

import com.sv.calorieintakeapps.feature_profile.domain.repository.IProfileRepository
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow

class ProfileInteractor(private val profileRepository: IProfileRepository) : ProfileUseCase {

    override fun getUserProfile(): Flow<Resource<User>> {
        return profileRepository.getUserProfile()
    }

    override fun editUserProfile(
        name: String,
        photoUri: String,
        gender: Gender,
        age: Int,
        password: String
    ): Flow<Resource<Boolean>> {
        return profileRepository.editUserProfile(
            name = name,
            photoUri = photoUri,
            gender = gender,
            age = age,
            password = password
        )
    }
}