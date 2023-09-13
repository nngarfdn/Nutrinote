package com.sv.calorieintakeapps.library_database.helper

import java.util.Locale

fun Double.roundOff(): Double {
    return String
        .format(Locale.ENGLISH, "%.2f", this) // Avoid decimal separator issue
        .toDouble()
}