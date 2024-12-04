package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model

data class FilterMakrotrienUiModel(
    val id: String,
    val title: String,
)

val listFilterMakrotrien = listOf(
    FilterMakrotrienUiModel("vit_c_total", "Vitamin C"),
    FilterMakrotrienUiModel("niasin_total", "Niasin"),
    FilterMakrotrienUiModel("rifobla_total", "Rifobla"),
    FilterMakrotrienUiModel("thiamin_total", "Thiamin"),
    FilterMakrotrienUiModel("karoten_total_total", "Karoten"),
    FilterMakrotrienUiModel("beta_karoten_total", "Beta Karoten"),
    FilterMakrotrienUiModel("retinol_total", "Retinol"),
    FilterMakrotrienUiModel("seng_total", "Seng"),
    FilterMakrotrienUiModel("tembaga_total", "Tembaga"),
    FilterMakrotrienUiModel("kalium_total", "Kalium"),
    FilterMakrotrienUiModel("natrium_total", "Natrium"),
    FilterMakrotrienUiModel("besi_total", "Besi"),
    FilterMakrotrienUiModel("fosfor_total", "Fosfor"),
    FilterMakrotrienUiModel("kalsium_total", "Kalsium"),
    FilterMakrotrienUiModel("abu_total", "Abu"),
    FilterMakrotrienUiModel("serat_total", "Serat"),
    FilterMakrotrienUiModel("total_karbohidrat", "Karbohidrat"),
    FilterMakrotrienUiModel("total_protein", "Protein"),
    FilterMakrotrienUiModel("total_lemak", "Lemak"),
    FilterMakrotrienUiModel("total_energi", "Energi"),
    FilterMakrotrienUiModel("total_air", "Air")
)