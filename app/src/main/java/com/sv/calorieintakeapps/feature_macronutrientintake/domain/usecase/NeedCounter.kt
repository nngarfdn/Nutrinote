package com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase

import com.sv.calorieintakeapps.library_database.domain.enum.Gender

object NeedsCounter {
    fun calculateCalciumNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 0..5 -> 200.0 // 0-5 months
            age in 6..11 -> 260.0 // 6-11 months
            age in 1..3 -> 700.0 // 1-3 years
            age in 4..6 -> 1000.0 // 4-6 years
            age in 7..9 -> 1000.0 // 7-9 years
            age in 10..18 -> 1200.0 // 10-18 years
            age in 19..64 && gender == Gender.FEMALE && age >= 50 -> 1200.0 // 50+ females
            age in 19..80 && gender == Gender.FEMALE -> 1000.0 // 19-49 females
            age in 19..80 && gender == Gender.MALE -> 1000.0 // 19-80 males
            age >= 80 && gender == Gender.FEMALE -> 1200.0 // 80+ females
            age >= 80 && gender == Gender.MALE -> 1000.0 // 80+ males
            else -> 1000.0 // Default
        }
    }

    fun calculateSeratNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 0..5 -> 0.0 // 0-5 months
            age in 6..11 -> 11.0 // 6-11 months
            age in 1..3 -> 19.0 // 1-3 years
            age in 4..6 -> 20.0 // 4-6 years
            age in 7..9 -> 23.0 // 7-9 years
            age in 10..12 -> 28.0 // 10-12 years
            age in 13..15 -> 34.0 // 13-15 years
            age in 16..18 -> 37.0 // 16-18 years
            age in 19..29 && gender == Gender.MALE -> 37.0 // 19-29 years male
            age in 19..29 && gender == Gender.FEMALE -> 32.0 // 19-29 years female
            age in 30..49 && gender == Gender.MALE -> 36.0 // 30-49 years male
            age in 30..49 && gender == Gender.FEMALE -> 30.0 // 30-49 years female
            age in 50..64 && gender == Gender.MALE -> 30.0 // 50-64 years male
            age in 50..64 && gender == Gender.FEMALE -> 25.0 // 50-64 years female
            age in 65..80 && gender == Gender.MALE -> 25.0 // 65-80 years male
            age in 65..80 && gender == Gender.FEMALE -> 21.0 // 65-80 years female
            age > 80 && gender == Gender.MALE -> 22.0 // 80+ years male
            age > 80 && gender == Gender.FEMALE -> 18.0 // 80+ years female
            else -> 0.0 // Default fallback
        }
    }
    fun calculateAbuNeeds(weight: Double): Double {
        return weight * 0.01
    }
    fun calculateFosforNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 460.0
            age in 4..6 -> 500.0
            age in 7..9 -> 500.0
            age in 10..18 -> 1250.0
            age >= 19 -> 700.0
            else -> 0.0
        }
    }
    fun calculateBesiNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 7.0
            age in 4..6 -> 10.0
            age in 7..9 -> 10.0
            age in 10..12 && gender == Gender.MALE -> 8.0
            age in 10..12 && gender == Gender.FEMALE -> 8.0
            age in 13..18 && gender == Gender.MALE -> 11.0
            age in 13..18 && gender == Gender.FEMALE -> 15.0
            age >= 19 && gender == Gender.MALE -> 9.0
            age >= 19 && gender == Gender.FEMALE -> 18.0
            else -> 0.0
        }
    }
    fun calculateNatriumNeeds(age: Int): Double {
        return when {
            age in 1..3 -> 800.0
            age in 4..6 -> 900.0
            age in 7..9 -> 1000.0
            age in 10..18 -> 1500.0
            age >= 19 -> 1500.0
            else -> 0.0
        }
    }
    fun calculateKaliumNeeds(age: Int): Double {
        return when {
            age in 1..3 -> 2600.0
            age in 4..6 -> 2700.0
            age in 7..9 -> 3200.0
            age in 10..12 -> 3900.0
            age in 13..18 -> 4700.0
            age >= 19 -> 4700.0
            else -> 0.0
        }
    }
    fun calculateTembagaNeeds(age: Int): Double {
        return when {
            age in 1..3 -> 340.0
            age in 4..6 -> 440.0
            age in 7..9 -> 570.0
            age in 10..18 -> 700.0
            age >= 19 -> 900.0
            else -> 0.0
        }
    }
    fun calculateSengNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 3.0
            age in 4..6 -> 5.0
            age in 7..9 -> 5.0
            age in 10..12 && gender == Gender.MALE -> 8.0
            age in 10..12 && gender == Gender.FEMALE -> 8.0
            age in 13..18 && gender == Gender.MALE -> 11.0
            age in 13..18 && gender == Gender.FEMALE -> 9.0
            age >= 19 && gender == Gender.MALE -> 11.0
            age >= 19 && gender == Gender.FEMALE -> 8.0
            else -> 0.0
        }
    }
    fun calculateRetinolNeeds(age: Int, gender: Gender): Double {
        return when (gender) {
            Gender.MALE -> when {
                age in 10..12 -> 600.0 // Boys (10–12 years)
                age in 13..15 -> 600.0 // Boys (13–15 years)
                age in 16..18 -> 700.0 // Boys (16–18 years)
                age in 19..29 -> 650.0 // Men (19–29 years)
                age in 30..49 -> 650.0 // Men (30–49 years)
                age in 50..64 -> 650.0 // Men (50–64 years)
                age in 65..80 -> 650.0 // Men (65–80 years)
                age > 80 -> 650.0 // Men (80+ years)
                else -> 0.0
            }
            Gender.FEMALE -> when {
                age in 10..12 -> 600.0 // Girls (10–12 years)
                age in 13..15 -> 600.0 // Girls (13–15 years)
                age in 16..18 -> 600.0 // Girls (16–18 years)
                age in 19..29 -> 600.0 // Women (19–29 years)
                age in 30..49 -> 600.0 // Women (30–49 years)
                age in 50..64 -> 600.0 // Women (50–64 years)
                age in 65..80 -> 600.0 // Women (65–80 years)
                age > 80 -> 600.0 // Women (80+ years)
                else -> 0.0
            }
        }
    }

    fun calculateBetaKarotenNeeds(age: Int, gender: Gender): Double {
        return calculateRetinolNeeds(age, gender) * 2.0
    }

    fun calculateKarotenNeeds(age: Int, gender: Gender): Double {
        return calculateBetaKarotenNeeds(age, gender) // Same as beta-carotene
    }

    fun calculateThiaminNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 0.8
            age in 4..6 -> 0.9
            age in 7..9 -> 1.0
            age in 10..12 -> 1.0
            age in 13..18 && gender == Gender.MALE -> 1.2
            age in 13..18 && gender == Gender.FEMALE -> 1.1
            age >= 19 && gender == Gender.MALE -> 1.2
            age >= 19 && gender == Gender.FEMALE -> 1.1
            else -> 0.0
        }
    }
    fun calculateRifoblaNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 0.9
            age in 4..6 -> 1.0
            age in 7..9 -> 1.2
            age in 10..12 -> 1.2
            age in 13..18 && gender == Gender.MALE -> 1.3
            age in 13..18 && gender == Gender.FEMALE -> 1.2
            age >= 19 && gender == Gender.MALE -> 1.3
            age >= 19 && gender == Gender.FEMALE -> 1.1
            else -> 0.0
        }
    }
    fun calculateNiasinNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 6.0
            age in 4..6 -> 8.0
            age in 7..9 -> 11.0
            age in 10..12 -> 13.0
            age in 13..18 && gender == Gender.MALE -> 16.0
            age in 13..18 && gender == Gender.FEMALE -> 14.0
            age >= 19 && gender == Gender.MALE -> 16.0
            age >= 19 && gender == Gender.FEMALE -> 14.0
            else -> 0.0
        }
    }
    fun calculateVitaminCNeeds(age: Int, gender: Gender): Double {
        return when {
            age in 1..3 -> 40.0
            age in 4..6 -> 45.0
            age in 7..9 -> 45.0
            age in 10..12 -> 50.0
            age in 13..18 && gender == Gender.MALE -> 75.0
            age in 13..18 && gender == Gender.FEMALE -> 65.0
            age >= 19 && gender == Gender.MALE -> 90.0
            age >= 19 && gender == Gender.FEMALE -> 75.0
            else -> 0.0
        }
    }



}