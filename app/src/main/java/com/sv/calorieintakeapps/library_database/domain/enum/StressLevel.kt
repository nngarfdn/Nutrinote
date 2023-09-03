package com.sv.calorieintakeapps.library_database.domain.enum

enum class StressLevel(val value: Double) {
    HEALTHY(1.0),
    SURGERY(1.0),
    TRAUMA(1.2),
    SEVERE_INFECTION(1.2),
    PERITONITIS(1.05),
    FRACTURE(1.1),
    INFECTION_WITH_TRAUMA(1.3),
    SEPSIS(1.2),
    HEAD_INJURY(1.3),
    CANCER(1.1),
    BURNS_0_20(1.0),
    BURNS_20_40(1.5),
    BURNS_40_100(1.85),
    FEVER(1.2),
}