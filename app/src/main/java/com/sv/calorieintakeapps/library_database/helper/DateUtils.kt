package com.sv.calorieintakeapps.library_database.helper

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toReadableDate(): String {
    return try {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = simpleDateFormat.parse(this)!!
        val readableDateFormat = SimpleDateFormat("d MMM yyyy", Locale("in", "ID"))
        readableDateFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}