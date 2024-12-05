package com.sv.calorieintakeapps.library_database.domain.model

import com.sv.calorieintakeapps.R
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

data class MacronutrientIntakePercentage(
    val caloriesNeeds: Double,
    val caloriesIntake: Double,
    val proteinNeeds: Double,
    val proteinIntake: Double,
    val fatNeeds: Double,
    val fatIntake: Double,
    val carbsNeeds: Double,
    val carbsIntake: Double,
    val waterNeeds: Double,
    val waterIntake: Double,
    val calciumNeeds: Double,
    val calciumIntake: Double,
    val seratNeeds: Double,
    val seratIntake: Double,
    val abuNeeds: Double,
    val abuIntake: Double,
    val fosforNeeds: Double,
    val fosforIntake: Double,
    val besiNeeds: Double,
    val besiIntake: Double,
    val natriumNeeds: Double,
    val natriumIntake: Double,
    val kaliumNeeds: Double,
    val kaliumIntake: Double,
    val tembagaNeeds: Double,
    val tembagaIntake: Double,
    val sengNeeds: Double,
    val sengIntake: Double,
    val retinolNeeds: Double,
    val retinolIntake: Double,
    val betaKarotenNeeds: Double,
    val betaKarotenIntake: Double,
    val karotenTotalNeeds: Double,
    val karotenTotalIntake: Double,
    val thiaminNeeds: Double,
    val thiaminIntake: Double,
    val rifoblaNeeds: Double,
    val rifoblaIntake: Double,
    val niasinNeeds: Double,
    val niasinIntake: Double,
    val vitaminCNeeds: Double,
    val vitaminCIntake: Double
) {

    val listMakronutrien = generateListMakronutrien()

    private val caloriesPercentage: Int
        get() = getPercentageNeedsIntake(caloriesNeeds, caloriesIntake)
    private val proteinPercentage: Int
        get() = getPercentageNeedsIntake(proteinNeeds, proteinIntake)
    private val fatPercentage: Int
        get() = getPercentageNeedsIntake(fatNeeds, fatIntake)
    private val carbsPercentage: Int
        get() = getPercentageNeedsIntake(carbsNeeds, carbsIntake)
    private val waterPercentage: Int
        get() = getPercentageNeedsIntake(waterNeeds, waterIntake)
    private val calciumPercentage: Int
        get() = getPercentageNeedsIntake(calciumNeeds, calciumIntake)
    private val seratPercentage: Int
        get() = getPercentageNeedsIntake(seratNeeds, seratIntake)
    private val abuPercentage: Int
        get() = getPercentageNeedsIntake(abuNeeds, abuIntake)
    private val fosforPercentage: Int
        get() = getPercentageNeedsIntake(fosforNeeds, fosforIntake)
    private val besiPercentage: Int
        get() = getPercentageNeedsIntake(besiNeeds, besiIntake)
    private val natriumPercentage: Int
        get() = getPercentageNeedsIntake(natriumNeeds, natriumIntake)
    private val kaliumPercentage: Int
        get() = getPercentageNeedsIntake(kaliumNeeds, kaliumIntake)
    private val tembagaPercentage: Int
        get() = getPercentageNeedsIntake(tembagaNeeds, tembagaIntake)
    private val sengPercentage: Int
        get() = getPercentageNeedsIntake(sengNeeds, sengIntake)
    private val retinolPercentage: Int
        get() = getPercentageNeedsIntake(retinolNeeds, retinolIntake)
    private val betaKarotenPercentage: Int
        get() = getPercentageNeedsIntake(betaKarotenNeeds, betaKarotenIntake)
    private val karotenTotalPercentage: Int
        get() = getPercentageNeedsIntake(karotenTotalNeeds, karotenTotalIntake)
    private val thiaminPercentage: Int
        get() = getPercentageNeedsIntake(thiaminNeeds, thiaminIntake)
    private val rifoblaPercentage: Int
        get() = getPercentageNeedsIntake(rifoblaNeeds, rifoblaIntake)
    private val niasinPercentage: Int
        get() = getPercentageNeedsIntake(niasinNeeds, niasinIntake)
    private val vitaminCPercentage: Int
        get() = getPercentageNeedsIntake(vitaminCNeeds, vitaminCIntake)

    private fun getPercentageNeedsIntake(needs: Double, intake: Double): Int {
        return if (needs == 0.0) {
            0
        } else {
            (intake / needs * 100).toInt()
        }
    }

    private fun generateListMakronutrien() = mutableListOf(
        ItemMakronutrien(
            TITLE_KARBOHIDRAT,
            R.drawable.ic_carbs_24,
            carbsPercentage,
            "$carbsIntake / $carbsNeeds g"
        ),
        ItemMakronutrien(
            TITLE_LEMAK,
            R.drawable.ic_fat_24,
            fatPercentage,
            "$fatIntake / $fatNeeds g"
        ),
        ItemMakronutrien(
            TITLE_AIR,
            R.drawable.water_drop_24px,
            waterPercentage,
            "$waterIntake / $waterNeeds ml"
        ),
        ItemMakronutrien(
            TITLE_ENERGI,
            R.drawable.ic_calories_24,
            caloriesPercentage,
            "$caloriesIntake / $caloriesNeeds kkal"
        ),
        ItemMakronutrien(
            TITLE_PROTEIN,
            R.drawable.ic_protein_24,
            proteinPercentage,
            "$proteinIntake / $proteinNeeds g"
        ),
        ItemMakronutrien(
            TITLE_KALSIUM,
            R.drawable.img_no_image_24,
            calciumPercentage,
            "$calciumIntake / $calciumNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_SERAT,
            R.drawable.img_no_image_24,
            seratPercentage,
            "$seratIntake / $seratNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_ABU,
            R.drawable.img_no_image_24,
            abuPercentage,
            "$abuIntake / $abuNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_FOSFOR,
            R.drawable.img_no_image_24,
            fosforPercentage,
            "$fosforIntake / $fosforNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_BESI,
            R.drawable.img_no_image_24,
            besiPercentage,
            "$besiIntake / $besiNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_NATRIUM,
            R.drawable.img_no_image_24,
            natriumPercentage,
            "$natriumIntake / $natriumNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_KALIUM,
            R.drawable.img_no_image_24,
            kaliumPercentage,
            "$kaliumIntake / $kaliumNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_TEMBAGA,
            R.drawable.img_no_image_24,
            tembagaPercentage,
            "$tembagaIntake / $tembagaNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_SENG,
            R.drawable.img_no_image_24,
            sengPercentage,
            "$sengIntake / $sengNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_RETINOL,
            R.drawable.img_no_image_24,
            retinolPercentage,
            "$retinolIntake / $retinolNeeds µg"
        ),
        ItemMakronutrien(
            TITLE_BETA_KAROTEN,
            R.drawable.img_no_image_24,
            betaKarotenPercentage,
            "$betaKarotenIntake / $betaKarotenNeeds µg"
        ),
        ItemMakronutrien(
            TITLE_KAROTEN_TOTAL,
            R.drawable.img_no_image_24,
            karotenTotalPercentage,
            "$karotenTotalIntake / $karotenTotalNeeds µg"
        ),
        ItemMakronutrien(
            TITLE_THIAMIN,
            R.drawable.img_no_image_24,
            thiaminPercentage,
            "$thiaminIntake / $thiaminNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_RIFOBLA,
            R.drawable.img_no_image_24,
            rifoblaPercentage,
            "$rifoblaIntake / $rifoblaNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_NIASIN,
            R.drawable.img_no_image_24,
            niasinPercentage,
            "$niasinIntake / $niasinNeeds mg"
        ),
        ItemMakronutrien(
            TITLE_VITAMIN_C,
            R.drawable.img_no_image_24,
            vitaminCPercentage,
            "$vitaminCIntake / $vitaminCNeeds mg"
        )
    )
}

