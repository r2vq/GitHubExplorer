package com.keanequibilan.githubexplorer.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.keanequibilan.githubexplorer.GITHUB_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(
    okHttpClient: OkHttpClient,
    coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
    gsonConverterFactory: GsonConverterFactory
) {

    private val client: GitHubService = Retrofit.Builder()
        .baseUrl(GITHUB_API_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(coroutineCallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(GitHubService::class.java)
}