package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel

data class Food(
    val id: Int = -1,
    val merchantId: Int = -1,
    val name: String = "",
    val price: String = "",
    val portion: String = "",
    val label: FoodLabel = FoodLabel.VERY_GOOD,
    val image: String = "",
)