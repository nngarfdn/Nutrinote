package com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase

import com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository.IMacronutrientIntakeRepository
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.helper.roundOff
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getReportByDate(date: String): Flow<Resource<List<ReportDomainModel>>> {
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
        reports: List<ReportDomainModel>,
    ): Flow<Resource<MacronutrientIntakePercentage>> {
        return macronutrientIntakeRepository.getUserProfile().map { userProfileResource ->
            var totalCalories = 0.0
            var totalProtein = 0.0
            var totalFat = 0.0
            var totalCarbs = 0.0
            var totalWater = 0.0
            var totalCalcium = 0.0
            var totalSerat = 0.0
            var totalAbu = 0.0
            var totalFosfor = 0.0
            var totalBesi = 0.0
            var totalNatrium = 0.0
            var totalKalium = 0.0
            var totalTembaga = 0.0
            var totalSeng = 0.0
            var totalRetinol = 0.0
            var totalBetaKaroten = 0.0
            var totalKarotenTotal = 0.0
            var totalThiamin = 0.0
            var totalRifobla = 0.0
            var totalNiasin = 0.0
            var totalVitaminC = 0.0

            reports.forEach { report ->
                val consumedPercentage =
                    (100 - (report.percentage?.toDouble() ?: 0.0)) / 100
                totalCalories += report.calories.toFloat() * consumedPercentage
                totalProtein += report.protein.toFloat() * consumedPercentage
                totalFat += report.fat.toFloat() * consumedPercentage
                totalCarbs += report.carbs.toFloat() * consumedPercentage
                totalWater += report.air.toFloat() * consumedPercentage
                totalCalcium += report.calcium?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalSerat += report.serat?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalAbu += report.abu?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalFosfor += report.fosfor?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalBesi += report.besi?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalNatrium += report.natrium?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalKalium += report.kalium?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalTembaga += report.tembaga?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalSeng += report.seng?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalRetinol += report.retinol?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalBetaKaroten += report.betaKaroten?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalKarotenTotal += report.karotenTotal?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalThiamin += report.thiamin?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalRifobla += report.rifobla?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalNiasin += report.niasin?.toDouble()?.times(consumedPercentage) ?: 0.0
                totalVitaminC += report.vitaminC?.toDouble()?.times(consumedPercentage) ?: 0.0
            }

            var caloriesNeeds = 0.0
            var proteinNeeds = 0.0
            var fatNeeds = 0.0
            var carbsNeeds = 0.0
            var waterNeeds = 2000
            var calciumNeeds = 10.0
            var seratNeeds = 10.0
            var abuNeeds = 10.0
            var fosforNeeds = 10.0
            var besiNeeds = 10.0
            var natriumNeeds = 10.0
            var kaliumNeeds = 10.0
            var tembagaNeeds = 10.0
            var sengNeeds = 10.0
            var retinolNeeds = 10.0
            var betaKarotenNeeds = 10.0
            var karotenTotalNeeds = 10.0
            var thiaminNeeds = 10.0
            var rifoblaNeeds = 10.0
            var niasinNeeds = 10.0
            var vitaminCNeeds = 10.0

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
                calciumNeeds = calciumNeeds * 16 / 4
                seratNeeds = seratNeeds * 0.6 / 4
                abuNeeds = abuNeeds * 0.6 / 4
                fosforNeeds = fosforNeeds * 0.6 / 4
                besiNeeds = besiNeeds * 0.6 / 4
                natriumNeeds = natriumNeeds * 0.6 / 4
                kaliumNeeds = kaliumNeeds * 0.6 / 4
                tembagaNeeds = tembagaNeeds * 0.6 / 4
                sengNeeds = sengNeeds * 0.6 / 4
                retinolNeeds = retinolNeeds * 0.6 / 4
                betaKarotenNeeds = betaKarotenNeeds * 0.6 / 4
                karotenTotalNeeds = karotenTotalNeeds * 0.6 / 4
                thiaminNeeds = thiaminNeeds * 0.6 / 4
                rifoblaNeeds = rifoblaNeeds * 0.6 / 4
                niasinNeeds = niasinNeeds * 0.6 / 4
                vitaminCNeeds = vitaminCNeeds * 0.6 / 4
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
                    waterNeeds = waterNeeds.toDouble(),
                    calciumIntake = totalCalcium.roundOff(),
                    calciumNeeds = calciumNeeds.roundOff(),
                    seratIntake = totalSerat.roundOff(),
                    seratNeeds = seratNeeds.roundOff(),
                    abuIntake = totalAbu.roundOff(),
                    abuNeeds = abuNeeds.roundOff(),
                    fosforIntake = totalFosfor.roundOff(),
                    fosforNeeds = fosforNeeds.roundOff(),
                    besiIntake = totalBesi.roundOff(),
                    besiNeeds = besiNeeds.roundOff(),
                    natriumIntake = totalNatrium.roundOff(),
                    natriumNeeds = natriumNeeds.roundOff(),
                    kaliumIntake = totalKalium.roundOff(),
                    kaliumNeeds = kaliumNeeds.roundOff(),
                    tembagaIntake = totalTembaga.roundOff(),
                    tembagaNeeds = tembagaNeeds.roundOff(),
                    sengIntake = totalSeng.roundOff(),
                    sengNeeds = sengNeeds.roundOff(),
                    retinolIntake = totalRetinol.roundOff(),
                    retinolNeeds = retinolNeeds.roundOff(),
                    betaKarotenIntake = totalBetaKaroten.roundOff(),
                    betaKarotenNeeds = betaKarotenNeeds.roundOff(),
                    karotenTotalIntake = totalKarotenTotal.roundOff(),
                    karotenTotalNeeds = karotenTotalNeeds.roundOff(),
                    thiaminIntake = totalThiamin.roundOff(),
                    thiaminNeeds = thiaminNeeds.roundOff(),
                    rifoblaIntake = totalRifobla.roundOff(),
                    rifoblaNeeds = rifoblaNeeds.roundOff(),
                    niasinIntake = totalNiasin.roundOff(),
                    niasinNeeds = niasinNeeds.roundOff(),
                    vitaminCIntake = totalVitaminC.roundOff(),
                    vitaminCNeeds = vitaminCNeeds.roundOff()
                )
            )
        }
    }
}
    
