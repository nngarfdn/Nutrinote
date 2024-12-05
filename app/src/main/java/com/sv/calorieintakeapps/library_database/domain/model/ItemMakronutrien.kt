package com.sv.calorieintakeapps.library_database.domain.model

data class ItemMakronutrien(
    val name: String,
    val image: Int=0,
    val percentage: Int,
    val value: String,
    var isActive: Boolean = true
)