package com.keanequibilan.githubexplorer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.keanequibilan.githubexplorer.network.RetrofitClient
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.converter.gson.GsonConverterFactory

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

}