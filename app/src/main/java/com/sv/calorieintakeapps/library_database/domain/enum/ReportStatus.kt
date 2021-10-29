package com.sv.calorieintakeapps.library_database.domain.enum

enum class ReportStatus(val id: String) {
    PENDING("proses"),
    COMPLETE("selesai");

    companion object {
        fun new(id: String) = values().find { it.id == id } ?: PENDING
    }
}