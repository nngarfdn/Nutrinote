package com.sv.calorieintakeapps.library_database.domain.enum

enum class Gender(val id: Int) {
    MALE(0),
    FEMALE(1);

    companion object {
        fun new(id: Int) = values().find { it.id == id } ?: MALE
    }
}