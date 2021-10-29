package com.sv.calorieintakeapps.feature_profile.di

import com.sv.calorieintakeapps.feature.profile.presentation.ProfileViewModel
import com.sv.calorieintakeapps.feature_profile.data.repository.ProfileRepository
import com.sv.calorieintakeapps.feature_profile.domain.repository.IProfileRepository
import com.sv.calorieintakeapps.feature_profile.domain.usecase.ProfileInteractor
import com.sv.calorieintakeapps.feature_profile.domain.usecase.ProfileUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IProfileRepository> {
        ProfileRepository(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<ProfileUseCase> { ProfileInteractor(profileRepository = get()) }
}

private val viewModelModule = module {
    viewModel { ProfileViewModel(useCase = get()) }
}

internal object ProfileModule {

    private val profileModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(profileModules)

    internal fun unload() = unloadKoinModules(profileModules)
}