package com.sv.calorieintakeapps.feature_merchantmenu.di

import com.sv.calorieintakeapps.feature_merchantmenu.data.repository.MerchantMenuRepository
import com.sv.calorieintakeapps.feature_merchantmenu.domain.repository.IMerchantMenuRepository
import com.sv.calorieintakeapps.feature_merchantmenu.domain.usecase.MerchantMenuInteractor
import com.sv.calorieintakeapps.feature_merchantmenu.domain.usecase.MerchantMenuUseCase
import com.sv.calorieintakeapps.feature_merchantmenu.presentation.MerchantMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IMerchantMenuRepository> {
        MerchantMenuRepository(remoteDataSource = get())
    }
}

private val useCaseModule = module {
    factory<MerchantMenuUseCase> { MerchantMenuInteractor(merchantMenuRepository = get()) }
}

private val viewModelModule = module {
    viewModel { MerchantMenuViewModel(merchantMenuUseCase = get()) }
}

internal object MerchantMenuModule {

    private val merchantMenuModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(merchantMenuModules)

    internal fun unload() = unloadKoinModules(merchantMenuModules)
}