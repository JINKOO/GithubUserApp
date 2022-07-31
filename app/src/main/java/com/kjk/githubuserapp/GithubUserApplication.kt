package com.kjk.githubuserapp

import android.app.Application
import com.kjk.githubuserapp.repo.GithubUserRepository

class GithubUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GithubUserRepository.initialize(this)
    }
}