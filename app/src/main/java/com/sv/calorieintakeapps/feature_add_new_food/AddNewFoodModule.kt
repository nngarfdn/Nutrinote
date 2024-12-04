package com.sv.calorieintakeapps.feature_add_new_food

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module


private val viewModelModule = module {
    viewModel { AddNewFoodViewModel(remoteDataSource = get()) }
}

internal object AddNewFoodModule {

    private val authModules = listOf(
        viewModelModule,
    )

    internal fun load() = loadKoinModules(authModules)

    internal fun unload() = unloadKoinModules(authModules)

}