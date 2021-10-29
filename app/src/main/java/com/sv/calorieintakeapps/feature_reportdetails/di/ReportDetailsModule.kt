package com.sv.calorieintakeapps.feature_reportdetails.di

import com.sv.calorieintakeapps.feature_reportdetails.data.repository.ReportDetailsRepository
import com.sv.calorieintakeapps.feature_reportdetails.domain.repository.IReportDetailsRepository
import com.sv.calorieintakeapps.feature_reportdetails.domain.usecase.ReportDetailsInteractor
import com.sv.calorieintakeapps.feature_reportdetails.domain.usecase.ReportDetailsUseCase
import com.sv.calorieintakeapps.feature_reportdetails.presentation.ReportDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IReportDetailsRepository> {
        ReportDetailsRepository(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<ReportDetailsUseCase> { ReportDetailsInteractor(reportDetailsRepository = get()) }
}

private val viewModelModule = module {
    viewModel { ReportDetailViewModel(useCase = get()) }
}

internal object ReportDetailsModule {

    private val reportDetailsModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(reportDetailsModules)

    internal fun unload() = unloadKoinModules(reportDetailsModules)
}