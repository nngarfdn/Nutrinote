package com.sv.calorieintakeapps.feature_profile

object BMI {

    fun calculate(weightKg: Float, heightM: Float): Float {
        if (weightKg <= 0 || heightM <= 0) {
            throw IllegalArgumentException("Height and weight must be greater than zero")
        }
        return weightKg / (heightM * heightM)
    }

    fun getBMICategory(weightKg: Float, heightM: Float): BMICategory {
        val bmi = calculate(weightKg, heightM)
        return when {
            bmi < 17.0 -> BMICategory.VERY_UNDERWEIGHT
            bmi in 17.1..18.5 -> BMICategory.UNDERWEIGHT
            bmi in 18.6..25.0 -> BMICategory.NORMAL
            bmi in 25.1..27.0 -> BMICategory.OVERWEIGHT
            bmi > 27.0 -> BMICategory.OBESITY
            else -> BMICategory.NA
        }
    }
}