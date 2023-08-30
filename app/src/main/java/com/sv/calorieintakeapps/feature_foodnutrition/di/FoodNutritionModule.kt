package com.sv.calorieintakeapps.feature_foodnutrition.di

import com.sv.calorieintakeapps.feature_foodnutrition.data.FoodNutritionRepository
import com.sv.calorieintakeapps.feature_foodnutrition.domain.repository.IFoodNutritionRepository
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionInteractor
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.feature_foodnutrition.presentation.details.FoodNutritionDetailsViewModel
import com.sv.calorieintakeapps.feature_foodnutrition.presentation.search.FoodNutritionSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IFoodNutritionRepository> {
        FoodNutritionRepository(
            remoteDataSource = get(),
            foodNutritionSearchPagingSource = get(),
        )
    }
}

private val useCaseModule = module {
    factory<FoodNutritionUseCase> {
        FoodNutritionInteractor(
            foodNutritionSearchRepository = get()
        )
    }
}

private val viewModelModule = module {
    viewModel { FoodNutritionSearchViewModel(foodNutritionUseCase = get()) }
    viewModel { FoodNutritionDetailsViewModel(foodNutritionUseCase = get()) }
}

internal object FoodNutritionModule {
    
    private val authModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )
    
    internal fun load() = loadKoinModules(authModules)
    
    internal fun unload() = unloadKoinModules(authModules)

}