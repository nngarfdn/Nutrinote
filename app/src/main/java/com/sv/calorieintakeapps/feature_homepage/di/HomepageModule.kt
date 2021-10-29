package com.sv.calorieintakeapps.feature_homepage.di

import com.sv.calorieintakeapps.feature_homepage.presentation.history.HistoryViewModel
import com.sv.calorieintakeapps.feature_homepage.presentation.home.HomeViewModel
import com.sv.calorieintakeapps.feature_homepage.data.repository.HomepageRepository
import com.sv.calorieintakeapps.feature_homepage.domain.repository.IHomepageRepository
import com.sv.calorieintakeapps.feature_homepage.domain.usecase.HomepageInteractor
import com.sv.calorieintakeapps.feature_homepage.domain.usecase.HomepageUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IHomepageRepository> {
        HomepageRepository(
            remoteDataSource = get(), localDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<HomepageUseCase> { HomepageInteractor(homepageRepository = get()) }
}

private val viewModelModule = module {
    viewModel { HistoryViewModel(useCase = get()) }
    viewModel { HomeViewModel(useCase = get()) }
}

internal object HomepageModule {

    private val homepageModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(homepageModules)

    internal fun unload() = unloadKoinModules(homepageModules)
}