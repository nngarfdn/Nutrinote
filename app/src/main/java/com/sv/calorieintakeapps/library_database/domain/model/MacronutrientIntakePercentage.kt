package com.sv.calorieintakeapps.library_database.domain.model

data class MacronutrientIntakePercentage(
    val caloriesNeeds: Double,
    val caloriesIntake: Double,
    val proteinNeeds: Double,
    val proteinIntake: Double,
    val fatNeeds: Double,
    val fatIntake: Double,
    val carbsNeeds: Double,
    val carbsIntake: Double,
) {
    
    val caloriesPercentage: Int
        get() = getPercentageNeedsIntake(caloriesNeeds, caloriesIntake)
    val proteinPercentage: Int
        get() = getPercentageNeedsIntake(proteinNeeds, proteinIntake)
    val fatPercentage: Int
        get() = getPercentageNeedsIntake(fatNeeds, fatIntake)
    val carbsPercentage: Int
        get() = getPercentageNeedsIntake(carbsNeeds, carbsIntake)
    
    private fun getPercentageNeedsIntake(needs: Double, intake: Double): Int {
        return if (needs == 0.0) {
            0
        } else {
            (intake / needs * 100).toInt()
        }
    }
    
}