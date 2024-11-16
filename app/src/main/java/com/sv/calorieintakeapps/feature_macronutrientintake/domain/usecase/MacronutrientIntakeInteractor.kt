package com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase

import com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository.IMacronutrientIntakeRepository
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.helper.roundOff
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class MacronutrientIntakeInteractor(
    private val macronutrientIntakeRepository: IMacronutrientIntakeRepository,
) : MacronutrientIntakeUseCase {
    
    override fun getInputDate(): Flow<Resource<List<String>>> {
        return macronutrientIntakeRepository.getUserReports()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val dates = resource.data.orEmpty()
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
        return macronutrientIntakeRepository.getUserProfile().map { userProfileResource ->
                        var totalCalories = 0.0
                        var totalProtein = 0.0
                        var totalFat = 0.0
                        var totalCarbs = 0.0
                        var totalWater = 0.0

                            reports.forEach { report ->
                                val consumedPercentage =
                                    (100 - (report.percentage?.toDouble() ?: 0.0)) / 100
                                totalCalories += report.calories.toFloat() * consumedPercentage
                                totalProtein += report.protein.toFloat() * consumedPercentage
                                totalFat += report.fat.toFloat() * consumedPercentage
                                totalCarbs += report.carbs.toFloat() * consumedPercentage
                                totalWater += report.air.toFloat() * consumedPercentage
                            }

                        var caloriesNeeds = 0.0
                        var proteinNeeds = 0.0
                        var fatNeeds = 0.0
                        var carbsNeeds = 0.0
                        var waterNeeds = 2000

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
                                waterIntake = totalWater.roundOff(),
                                waterNeeds = waterNeeds.toDouble()
                            )
                        )
                }
            }
    }
    
