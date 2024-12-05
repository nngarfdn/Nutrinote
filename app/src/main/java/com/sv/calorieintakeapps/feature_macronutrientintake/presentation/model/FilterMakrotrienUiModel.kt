package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model

import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_ABU
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_AIR
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_BESI
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_BETA_KAROTEN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_ENERGI
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_FOSFOR
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_KALIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_KALSIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_KARBOHIDRAT
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_KAROTEN_TOTAL
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_LEMAK
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_NATRIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_NIASIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_PROTEIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_RETINOL
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_RIFOBLA
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_SENG
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_SERAT
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_TEMBAGA
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_THIAMIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.ID_VITAMIN_C
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_ABU
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_AIR
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_BESI
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_BETA_KAROTEN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_ENERGI
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_FOSFOR
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_KALIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_KALSIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_KARBOHIDRAT
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_KAROTEN_TOTAL
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_LEMAK
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_NATRIUM
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_NIASIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_PROTEIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_RETINOL
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_RIFOBLA
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_SENG
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_SERAT
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_TEMBAGA
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_THIAMIN
import com.sv.calorieintakeapps.library_database.domain.model.MakrotrienData.TITLE_VITAMIN_C

data class FilterMakrotrienUiModel(
    val id: String,
    val title: String,
)

val listFilterMakrotrien = listOf(
    FilterMakrotrienUiModel(ID_KARBOHIDRAT, TITLE_KARBOHIDRAT),
    FilterMakrotrienUiModel(ID_LEMAK, TITLE_LEMAK),
    FilterMakrotrienUiModel(ID_AIR, TITLE_AIR),
    FilterMakrotrienUiModel(ID_ENERGI, TITLE_ENERGI),
    FilterMakrotrienUiModel(ID_PROTEIN, TITLE_PROTEIN),
    FilterMakrotrienUiModel(ID_KALSIUM, TITLE_KALSIUM),
    FilterMakrotrienUiModel(ID_SERAT, TITLE_SERAT),
    FilterMakrotrienUiModel(ID_ABU, TITLE_ABU),
    FilterMakrotrienUiModel(ID_FOSFOR, TITLE_FOSFOR),
    FilterMakrotrienUiModel(ID_BESI, TITLE_BESI),
    FilterMakrotrienUiModel(ID_NATRIUM, TITLE_NATRIUM),
    FilterMakrotrienUiModel(ID_KALIUM, TITLE_KALIUM),
    FilterMakrotrienUiModel(ID_TEMBAGA, TITLE_TEMBAGA),
    FilterMakrotrienUiModel(ID_SENG, TITLE_SENG),
    FilterMakrotrienUiModel(ID_RETINOL, TITLE_RETINOL),
    FilterMakrotrienUiModel(ID_BETA_KAROTEN, TITLE_BETA_KAROTEN),
    FilterMakrotrienUiModel(ID_KAROTEN_TOTAL, TITLE_KAROTEN_TOTAL),
    FilterMakrotrienUiModel(ID_THIAMIN, TITLE_THIAMIN),
    FilterMakrotrienUiModel(ID_RIFOBLA, TITLE_RIFOBLA),
    FilterMakrotrienUiModel(ID_NIASIN, TITLE_NIASIN),
    FilterMakrotrienUiModel(ID_VITAMIN_C, TITLE_VITAMIN_C),
)


