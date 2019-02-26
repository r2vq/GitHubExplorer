package com.keanequibilan.githubexplorer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.keanequibilan.githubexplorer.network.RetrofitClient
import com.keanequibilan.githubexplorer.viewmodel.GitHubViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

val APP_MODULE = module {
    /**
     * Provides the [RetrofitClient]. Depends on [OkHttpClient], [CoroutineCallAdapterFactory],
     * and [GsonConverterFactory].
     */
    single { RetrofitClient(get(), get(), get()) }

    /**
     * Provides the [OkHttpClient].
     */
    single { OkHttpClient() }

    /**
     * Provides the [CoroutineCallAdapterFactory].
     */
    single { CoroutineCallAdapterFactory() }

    /**
     * Provides the [GsonConverterFactory].
     */
    single { GsonConverterFactory.create() }

    /**
     * Provides a [CoroutineContext] specifically for IO tasks.
     */
    single<CoroutineContext>("backgroundContext") { Dispatchers.IO }

    /**
     * Provides the [GitHubViewModel]. Depends on [RetrofitClient] and a background [CoroutineContext].
     */
    viewModel { GitHubViewModel(get(), get("backgroundContext")) }

}