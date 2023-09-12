package com.sv.calorieintakeapps.library_database.domain.enum

enum class StressLevel(val id: Int, val value: Double) {
    HEALTHY(1, 1.0),
    SURGERY(2, 1.0),
    TRAUMA(3, 1.2),
    SEVERE_INFECTION(4, 1.2),
    PERITONITIS(5, 1.05),
    FRACTURE(6, 1.1),
    INFECTION_WITH_TRAUMA(7, 1.3),
    SEPSIS(8, 1.2),
    HEAD_INJURY(9, 1.3),
    CANCER(10, 1.1),
    BURNS_0_20(11, 1.0),
    BURNS_20_40(12, 1.5),
    BURNS_40_100(13, 1.85),
    FEVER(14, 1.2);
    
    companion object {
        
        fun new(id: Int) = StressLevel.values().find { it.id == id }
            ?: HEALTHY
        
    }
    
}