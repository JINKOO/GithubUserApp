package com.kjk.githubuserapp

import android.app.Application
import com.kjk.githubuserapp.repo.GithubUserRepository

/**
 *  application 클래스
 *  여기서 repository를 초기화 한다.
 *  각 viewModel에서 singleton으로 사용할 수 있도록 한다.
 */
class GithubUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GithubUserRepository.initialize(this)
    }
}