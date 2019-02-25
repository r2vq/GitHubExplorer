package com.keanequibilan.githubexplorer.network

import com.keanequibilan.githubexplorer.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/users/{name}")
    fun getUsersAsync(@Path("name") name: String): Deferred<User>
}