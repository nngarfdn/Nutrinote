package com.sv.calorieintakeapps.feature_macronutrientintake.di

import com.sv.calorieintakeapps.feature_macronutrientintake.data.MacronutrientIntakeRepository
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.repository.IMacronutrientIntakeRepository
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase.MacronutrientIntakeInteractor
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase.MacronutrientIntakeUseCase
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.input.MacronutrientIntakeInputViewModel
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.results.MacronutrientIntakeResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IMacronutrientIntakeRepository> {
        MacronutrientIntakeRepository(
            remoteDataSource = get(), localDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<MacronutrientIntakeUseCase> {
        MacronutrientIntakeInteractor(
            macronutrientIntakeRepository = get()
        )
    }
}

private val viewModelModule = module {
    viewModel { MacronutrientIntakeInputViewModel(useCase = get()) }
    viewModel { MacronutrientIntakeResultViewModel(useCase = get()) }
}

internal object MacronurientIntakeModule {
    
    private val macronurientIntakeModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )
    
    internal fun load() = loadKoinModules(macronurientIntakeModules)
    
    internal fun unload() = unloadKoinModules(macronurientIntakeModules)
    
}