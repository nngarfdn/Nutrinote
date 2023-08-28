package com.sv.calorieintakeapps.library_database.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sv.calorieintakeapps.BuildConfig.*
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.local.persistence.LoginSessionPreference
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.network.ApiService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_CALL_TIMEOUT = 120

val databaseModule = module {
    single { LoginSessionPreference(context = get()) }
}

val dataSourceModule = module {
    single { LocalDataSource(loginSessionPreference = get()) }
    single { RemoteDataSource(apiService = get()) }
}

val networkModule = module {
    single {
        val hostname = GIZI_BASE_URL.getHost()
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/$GIZI_PUBLIC_KEY_1")
            .add(hostname, "sha256/$GIZI_PUBLIC_KEY_2")
            .add(hostname, "sha256/$GIZI_PUBLIC_KEY_3")
            .build()

        val httpClient = OkHttpClient.Builder()
        with(httpClient) {
            if (DEBUG) addInterceptor(
                ChuckerInterceptor.Builder(context = get()).build()
            )
            connectTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            certificatePinner(certificatePinner)
            build()
        }
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(GIZI_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

private fun String?.getHost(): String {
    return this?.split("/")?.get(2).orEmpty()
}