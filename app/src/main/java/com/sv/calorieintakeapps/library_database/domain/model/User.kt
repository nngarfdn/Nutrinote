package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.Gender

data class User(
    val id: Int = -1,
    val name: String = "",
    val photo: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.MALE,
    val age: Int = 0,
)