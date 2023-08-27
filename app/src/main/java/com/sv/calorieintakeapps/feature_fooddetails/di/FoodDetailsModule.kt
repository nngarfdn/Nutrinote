package com.sv.calorieintakeapps.feature_fooddetails.di

import com.sv.calorieintakeapps.feature_fooddetails.data.repository.FoodDetailsRepository
import com.sv.calorieintakeapps.feature_fooddetails.domain.repository.IFoodDetailsRepository
import com.sv.calorieintakeapps.feature_fooddetails.domain.usecase.FoodDetailsInteractor
import com.sv.calorieintakeapps.feature_fooddetails.domain.usecase.FoodDetailsUseCase
import com.sv.calorieintakeapps.feature_fooddetails.presentation.FoodDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IFoodDetailsRepository> {
        FoodDetailsRepository( localDataSource = get(),remoteDataSource = get())
    }
}

private val useCaseModule = module {
    factory<FoodDetailsUseCase> { FoodDetailsInteractor(foodDetailsRepository = get()) }
}

private val viewModelModule = module {
    viewModel { FoodDetailViewModel(foodDetailsUseCase = get()) }
}

internal object FoodDetailsModule {

    private val foodDetailsModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(foodDetailsModules)

    internal fun unload() = unloadKoinModules(foodDetailsModules)
}