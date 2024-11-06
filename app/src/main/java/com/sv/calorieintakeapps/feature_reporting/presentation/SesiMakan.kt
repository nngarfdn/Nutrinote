package com.sv.calorieintakeapps.feature_reporting.presentation

import java.util.Calendar

data class SesiMakan(
    val sesi: String,
    val jam: Calendar
) {

    override fun toString(): String {
        return sesi
    }
}
