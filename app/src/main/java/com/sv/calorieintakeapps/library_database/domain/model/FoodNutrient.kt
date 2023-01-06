package com.sv.calorieintakeapps.library_database.domain.model

data class FoodNutrient(
    val id: Int = -1,
    val foodId: Int = -1,
    val nutrientId: Int = -1,
    val nutrientName: String = "",
    val nutrientUnit: String = "",
    val value: Double = 0.0,
    val akgDay: String = "",
)