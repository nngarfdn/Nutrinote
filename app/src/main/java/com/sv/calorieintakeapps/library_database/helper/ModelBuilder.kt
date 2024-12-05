package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.domain.model.User


object UserBuilder {
    
    fun update(
        id: Int,
        name: String,
        photoUri: String,
        password: String,
        gender: Gender,
        age: Int,
        height: Int,
        weight: Int,
        activityLevel: ActivityLevel,
        stressLevel: StressLevel,
    ): User {
        return User(
            id = id,
            name = name,
            photo = photoUri,
            password = password,
            gender = gender,
            age = age,
            height = height,
            weight = weight,
            activityLevel = activityLevel,
            stressLevel = stressLevel,
        )
    }
    
}