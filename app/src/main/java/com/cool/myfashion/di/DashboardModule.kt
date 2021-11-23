package com.cool.myfashion.di


import com.cool.myfashion.BuildConfig.BASE_URL
import com.cool.myfashion.base.DetectConnection
import com.cool.myfashion.network.DashboardRepository
import com.cool.myfashion.network.DashboardService
import com.cool.myfashion.viewmodel.DashboardViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by rahul.p
 *
 */

val dashboardModule = module {
    viewModel {
        DashboardViewModel(repo = get(), application = get())
    }

    single {
        DashboardRepository(
            service = get(),
            connectionUtil = DetectConnection
        )
    }

    factory<DashboardService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
            .create(DashboardService::class.java)
    }
}