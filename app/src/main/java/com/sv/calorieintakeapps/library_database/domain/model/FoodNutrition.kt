package com.sv.calorieintakeapps.library_database.domain.model

data class FoodNutrition(
    val foodId: Int,
    val name: String,
    val imageUrl: String,
    val calories: String,
    val protein: String,
    val fat: String,
    val carbs: String,
)