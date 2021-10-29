package com.sv.calorieintakeapps.feature_merchantlist.di

import com.sv.calorieintakeapps.feature_merchantlist.data.repository.MerchantListRepository
import com.sv.calorieintakeapps.feature_merchantlist.domain.repository.IMerchantListRepository
import com.sv.calorieintakeapps.feature_merchantlist.domain.usecase.MerchantListInteractor
import com.sv.calorieintakeapps.feature_merchantlist.domain.usecase.MerchantListUseCase
import com.sv.calorieintakeapps.feature_merchantlist.presentation.MerchantListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IMerchantListRepository> {
        MerchantListRepository(remoteDataSource = get())
    }
}

private val useCaseModule = module {
    factory<MerchantListUseCase> { MerchantListInteractor(merchantListRepository = get()) }
}

private val viewModelModule = module {
    viewModel { MerchantListViewModel(useCase = get()) }
}

internal object MerchantListModule {

    private val merchantListModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(merchantListModules)

    internal fun unload() = unloadKoinModules(merchantListModules)
}