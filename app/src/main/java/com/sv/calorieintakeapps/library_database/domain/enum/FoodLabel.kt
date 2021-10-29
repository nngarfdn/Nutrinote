package com.sv.calorieintakeapps.library_database.domain.enum

enum class FoodLabel(val id: String) {
    VERY_GOOD("sangat baik"),
    GOOD("baik"),
    BAD("buruk");

    companion object {
        fun new(id: String) = values().find { it.id == id } ?: VERY_GOOD
    }
}