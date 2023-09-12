package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel

data class User(
    val id: Int = -1,
    val name: String = "",
    val photo: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.MALE,
    val age: Int = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val activityLevel: ActivityLevel? = ActivityLevel.SEDENTARY,
    val stressLevel: StressLevel? = StressLevel.HEALTHY,
)