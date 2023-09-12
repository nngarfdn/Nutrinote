package com.sv.calorieintakeapps.feature_profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sv.calorieintakeapps.feature_profile.domain.usecase.ProfileUseCase
import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.vo.Resource

class ProfileViewModel(val useCase: ProfileUseCase) : ViewModel() {
    
    val userProfile = useCase.getUserProfile().asLiveData()
    
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
    ): LiveData<Resource<Boolean>> =
        useCase.editUserProfile(
            name,
            photoUri,
            gender,
            age,
            password,
            height,
            weight,
            activityLevel,
            stressLevel,
        ).asLiveData()
    
}