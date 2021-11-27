package com.cool.myfashion.di


import com.cool.myfashion.BuildConfig
import com.cool.myfashion.base.DetectConnection
import com.cool.myfashion.network.DashboardRepository
import com.cool.myfashion.network.DashboardService
import com.cool.myfashion.viewmodel.DashboardViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by rahul.p
 *
 */

val dashboardModule = module {

    single {
        DashboardRepository(
            service = get(),
            connectionUtil = DetectConnection
        )
    }
    single {
        OkHttpClient.Builder().apply {
            readTimeout(20L, TimeUnit.SECONDS)
            connectTimeout(20L, TimeUnit.SECONDS)
        }.build()
    }


    factory<DashboardService> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(DashboardService::class.java)
    }

    viewModel {
        DashboardViewModel(repo = get(), application = get())
    }

}