package com.sv.calorieintakeapps.library_database.domain.enum

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class FoodLabel(val id: String) : Parcelable{
    VERY_GOOD("sangat baik"),
    GOOD("baik"),
    BAD("buruk");
    companion object {
        fun new(id: String) = values().find { it.id == id } ?: VERY_GOOD
    }
}