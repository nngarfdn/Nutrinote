package com.sv.calorieintakeapps.library_database.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sv.calorieintakeapps.BuildConfig.DEBUG
import com.sv.calorieintakeapps.BuildConfig.GIZI_BASE_URL
import com.sv.calorieintakeapps.BuildConfig.GIZI_PUBLIC_KEY_1
import com.sv.calorieintakeapps.BuildConfig.GIZI_PUBLIC_KEY_2
import com.sv.calorieintakeapps.BuildConfig.GIZI_PUBLIC_KEY_3
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_BASE_URL
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_PUBLIC_KEY_1
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_PUBLIC_KEY_2
import com.sv.calorieintakeapps.BuildConfig.NILAIGIZI_COM_PUBLIC_KEY_3
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.local.persistence.LoginSessionPreference
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportDao
import com.sv.calorieintakeapps.library_database.data.source.local.room.ReportDatabase
import com.sv.calorieintakeapps.library_database.data.source.remote.FoodNutritionSearchPagingSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.main.MainApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.NilaigiziComApiService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val databaseModule = module {
    single { LoginSessionPreference(context = get()) }
    single { ReportDatabase.getDatabase(context = get()) }
}

val dataSourceModule = module {
    single { LocalDataSource(loginSessionPreference = get(), reportDao = get<ReportDatabase>().reportDao()) }
    single {
        RemoteDataSource(
            mainApiService = get(),
            nilaigiziComApiService = get(),
        )
    }
    single { FoodNutritionSearchPagingSource(nilaigiziComApiService = get()) }
}

val networkModule = module {
    single {
        val hostnameGizi = GIZI_BASE_URL.getHost()
        val hostnameNilaigizi = NILAIGIZI_COM_BASE_URL.getHost()
        
        val certificatePinner = CertificatePinner.Builder()
            .add(hostnameGizi, "sha256/$GIZI_PUBLIC_KEY_1")
            .add(hostnameGizi, "sha256/$GIZI_PUBLIC_KEY_2")
            .add(hostnameGizi, "sha256/$GIZI_PUBLIC_KEY_3")
            .add(hostnameNilaigizi, "sha256/$NILAIGIZI_COM_PUBLIC_KEY_1")
            .add(hostnameNilaigizi, "sha256/$NILAIGIZI_COM_PUBLIC_KEY_2")
            .add(hostnameNilaigizi, "sha256/$NILAIGIZI_COM_PUBLIC_KEY_3")
            .build()
        
        val httpClient = OkHttpClient.Builder()
        with(httpClient) {
            if (DEBUG) addInterceptor(
                ChuckerInterceptor.Builder(context = get()).build()
            )
            certificatePinner(certificatePinner)
            build()
        }
    }
    single {
        Retrofit.Builder()
            .baseUrl(GIZI_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
            .create(MainApiService::class.java)
    }
    single {
        Retrofit.Builder()
            .baseUrl(NILAIGIZI_COM_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
            .create(NilaigiziComApiService::class.java)
    }
}

private fun String?.getHost(): String {
    return this?.split("/")?.get(2).orEmpty()
}