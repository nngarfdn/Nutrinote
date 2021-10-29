package com.sv.calorieintakeapps.feature_auth.di

import com.sv.calorieintakeapps.feature_auth.data.repository.AuthRepository
import com.sv.calorieintakeapps.feature_auth.domain.repository.IAuthRepository
import com.sv.calorieintakeapps.feature_auth.domain.usecase.AuthInteractor
import com.sv.calorieintakeapps.feature_auth.domain.usecase.AuthUseCase
import com.sv.calorieintakeapps.feature_auth.presentation.login.LoginViewModel
import com.sv.calorieintakeapps.feature_auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

private val repositoryModule = module {
    single<IAuthRepository> { AuthRepository(remoteDataSource = get(), localDataSource = get()) }
}

private val useCaseModule = module {
    factory<AuthUseCase> { AuthInteractor(authRepository = get()) }
}

private val viewModelModule = module {
    viewModel { LoginViewModel(authUseCase = get()) }
    viewModel { RegisterViewModel(authUseCase = get()) }
}

internal object AuthModule {

    private val authModules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(authModules)

    internal fun unload() = unloadKoinModules(authModules)
}