package com.kjk.githubuserapp.repo

import com.kjk.githubuserapp.data.mapper.toUserEntity
import com.kjk.githubuserapp.data.remote.network.GithubUserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubUserRepository() {

    suspend fun getUsersFromNetwork() = withContext(Dispatchers.IO) {
        GithubUserApi.gitHubUserApiService.getAllUsers().items.toUserEntity()
    }
}