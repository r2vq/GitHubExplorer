package com.keanequibilan.githubexplorer

import android.annotation.SuppressLint
import android.app.Application
import com.keanequibilan.githubexplorer.di.APP_MODULE
import org.koin.android.ext.android.startKoin

@SuppressLint("Registered")
class GitHubExplorerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            modules = listOf(APP_MODULE)
        )
    }
}