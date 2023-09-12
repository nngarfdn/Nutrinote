package com.sv.calorieintakeapps.library_database.domain.enum

enum class ActivityLevel(val id: Int, val value: Double) {
    SEDENTARY(1, 1.2),
    LIGHTLY_ACTIVE(2, 1.375),
    MODERATELY_ACTIVE(3, 1.55),
    VERY_ACTIVE(4, 1.725),
    EXTRA_ACTIVE(5, 1.9);
    
    companion object {
        
        fun new(id: Int) = ActivityLevel.values().find { it.id == id }
            ?: SEDENTARY
        
    }
    
}