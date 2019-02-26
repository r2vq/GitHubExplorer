package com.keanequibilan.githubexplorer.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.keanequibilan.githubexplorer.GITHUB_API_BASE_URL
import com.keanequibilan.githubexplorer.model.Repo
import com.keanequibilan.githubexplorer.model.User
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
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

    /**
     * Retrieves the user returned by the GitHub API.
     *
     * @param name String the login ID of the user.
     */
    fun getUserAsync(name: String): Deferred<Response<User>> = client
        .getUsersAsync(name)

    /**
     * Retrieves the repos returned by the GitHub API for the specified User.
     *
     * @param name String the login ID of the user.
     */
    fun getReposAsync(name: String): Deferred<Response<List<Repo>>> = client
        .getReposAsync(name)
}