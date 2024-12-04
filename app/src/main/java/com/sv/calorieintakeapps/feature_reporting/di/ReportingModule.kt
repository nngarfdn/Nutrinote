package com.sv.calorieintakeapps.feature_reporting.di

import com.sv.calorieintakeapps.feature_reporting.domain.repository.IReportingRepository
import com.sv.calorieintakeapps.feature_reporting.domain.usecase.ReportingInteractor
import com.sv.calorieintakeapps.feature_reporting.domain.usecase.ReportingUseCase
import com.sv.calorieintakeapps.feature_reporting.presentation.ReportingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val useCaseModule = module {
    factory<ReportingUseCase> { ReportingInteractor(reportingRepository = get()) }
}

private val viewModelModule = module {
    viewModel { ReportingViewModel(localDataSource = get(), remoteDataSource = get()) }
}

internal object ReportingModule {

    private val reportingModules = listOf(
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(reportingModules)

    internal fun unload() = unloadKoinModules(reportingModules)
}