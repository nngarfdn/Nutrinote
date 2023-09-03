package com.sv.calorieintakeapps.library_database.domain.enum

enum class ActivityLevel(val value: Double) {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTRA_ACTIVE(1.9),
}