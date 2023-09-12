package com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase

import com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository.IMacronutrientIntakeRepository
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import java.util.Locale

class MacronutrientIntakeInteractor(
    private val macronutrientIntakeRepository: IMacronutrientIntakeRepository,
) : MacronutrientIntakeUseCase {
    
    override fun getInputDate(): Flow<Resource<List<String>>> {
        return macronutrientIntakeRepository.getUserReports()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val dates = resource.data.orEmpty()
                            .filter { it.status == ReportStatus.COMPLETE }
                            .map { it.date.split(" ").first() }
                            .distinct()
                        Resource.Success(dates)
                    }
                    
                    is Resource.Error -> {
                        Resource.Error(resource.message.orEmpty())
                    }
                    
                    is Resource.Loading -> {
                        Resource.Loading()
                    }
                }
            }
    }
    
    override fun getReportByDate(date: String): Flow<Resource<List<Report>>> {
        return macronutrientIntakeRepository.getUserReports()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val reports = resource.data.orEmpty()
                            .filter {
                                it.status == ReportStatus.COMPLETE &&
                                        it.date.split(" ").first() == date
                            }
                        Resource.Success(reports)
                    }
                    
                    is Resource.Error -> {
                        Resource.Error(resource.message.orEmpty())
                    }
                    
                    is Resource.Loading -> {
                        Resource.Loading()
                    }
                }
            }
    }
    
    override fun getMacronutrientIntakePercentage(
        reports: List<Report>,
    ): Flow<Resource<MacronutrientIntakePercentage>> {
        val foodIds = reports.filter { it.foodId != null }.map { it.foodId!! }
        return macronutrientIntakeRepository.getUserProfile()
            .zip(macronutrientIntakeRepository.getFoodNutrientsByFoodIds(foodIds)) { userProfileResource, foodNutrientsResource ->
                when (foodNutrientsResource) {
                    is Resource.Success -> {
                        var totalCalories = 0.0
                        var totalProtein = 0.0
                        var totalFat = 0.0
                        var totalCarbs = 0.0
                        
                        foodNutrientsResource.data?.let { foodNutrients ->
                            foodNutrients.distinct()
                            reports.forEach { report ->
                                val foodNutrientsOfSingleFood = foodNutrients
                                    .filter { it.foodId == report.foodId }
                                val consumedPercentage =
                                    (100 - (report.percentage?.toDouble() ?: 0.0)) / 100
                                totalCalories += (foodNutrientsOfSingleFood.firstOrNull { it.nutrientName == "Energi" }
                                    ?.value ?: 0.0) * consumedPercentage
                                totalProtein += (foodNutrientsOfSingleFood.firstOrNull { it.nutrientName == "Protein" }
                                    ?.value ?: 0.0) * consumedPercentage
                                totalFat += (foodNutrientsOfSingleFood.firstOrNull { it.nutrientName == "Lemak" }
                                    ?.value ?: 0.0) * consumedPercentage
                                totalCarbs += (foodNutrientsOfSingleFood.firstOrNull { it.nutrientName == "Karbohidrat" }
                                    ?.value ?: 0.0) * consumedPercentage
                            }
                        }
                        
                        var caloriesNeeds = 0.0
                        var proteinNeeds = 0.0
                        var fatNeeds = 0.0
                        var carbsNeeds = 0.0
                        
                        userProfileResource.data?.let { user ->
                            val bmr = if (user.gender == Gender.MALE) {
                                88.4 + (13.4 * user.weight) +
                                        (4.8 * user.height) - (5.68 * user.age)
                            } else {
                                447.6 + (9.25 * user.weight) +
                                        (3.1 * user.height) - (4.33 * user.age)
                            }
                            caloriesNeeds = bmr *
                                    (user.activityLevel?.value ?: 1.0) *
                                    (user.stressLevel?.value ?: 1.0)
                            proteinNeeds = caloriesNeeds * 0.15 / 4
                            fatNeeds = caloriesNeeds * 0.25 / 9
                            carbsNeeds = caloriesNeeds * 0.6 / 4
                        }
                        
                        Resource.Success(
                            MacronutrientIntakePercentage(
                                caloriesNeeds = caloriesNeeds.roundOff(),
                                caloriesIntake = totalCalories.roundOff(),
                                proteinNeeds = proteinNeeds.roundOff(),
                                proteinIntake = totalProtein.roundOff(),
                                fatNeeds = fatNeeds.roundOff(),
                                fatIntake = totalFat.roundOff(),
                                carbsNeeds = carbsNeeds.roundOff(),
                                carbsIntake = totalCarbs.roundOff(),
                            )
                        )
                    }
                    
                    is Resource.Error -> {
                        Resource.Error(foodNutrientsResource.message.orEmpty())
                    }
                    
                    is Resource.Loading -> {
                        Resource.Loading()
                    }
                }
            }
    }
    
    private fun Double.roundOff(): Double {
        return String
            .format(Locale.ENGLISH, "%.2f", this) // Avoid decimal separator issue
            .toDouble()
    }
    
}