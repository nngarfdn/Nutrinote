package com.sv.calorieintakeapps.feature_reporting.di

import com.sv.calorieintakeapps.feature_foodnutrition.presentation.details.FoodNutritionDetailsViewModel
import com.sv.calorieintakeapps.feature_foodnutrition.presentation.search.FoodNutritionSearchViewModel
import com.sv.calorieintakeapps.feature_reporting.presentation.UrtFoodSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { UrtFoodSearchViewModel(localDataSource = get(), remoteDataSource = get()) }
}

internal object UrtFoodModule {

    private val authModules = listOf(
        viewModelModule,
    )

    internal fun load() = loadKoinModules(authModules)

    internal fun unload() = unloadKoinModules(authModules)

}