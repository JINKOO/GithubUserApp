package com.kjk.githubuserapp.repo

import android.app.Application
import com.kjk.githubuserapp.data.local.GithubUserDatabase
import com.kjk.githubuserapp.data.mapper.toGithubUserVOList
import com.kjk.githubuserapp.data.remote.network.GithubUserApi
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.domain.mapper.toGitHubUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class GithubUserRepository(
    application: Application
) {


    private val database = GithubUserDatabase.getInstance(application)


    suspend fun getUsersFromNetwork(searchKeyword: String) = withContext(Dispatchers.IO) {
        GithubUserApi.gitHubUserApiService.searchUsers(searchKeyword).items.toGithubUserVOList()
    }

    suspend fun getFavoriteUserFromLocal() = withContext(Dispatchers.IO) {
        database.githubUserDatabaseDao.getFavoriteUsers()
    }

    suspend fun addFavoriteUser(githubUserVO: GithubUserVO) {
        withContext(Dispatchers.IO) {
            database.githubUserDatabaseDao.insertFavoriteUser(githubUserVO.toGitHubUserEntity())
        }
    }

    suspend fun deleteFavoriteUser(githubUserVO: GithubUserVO) {
        withContext(Dispatchers.IO) {
            database.githubUserDatabaseDao.deleteFavoriteUser(githubUserVO.toGitHubUserEntity())
        }
    }


    companion object {
        private const val TAG = "GithubUserRepository"
        private var INSTANCE: GithubUserRepository? = null

        fun initialize(application: Application) {
            if (INSTANCE == null) {
                INSTANCE = GithubUserRepository(application)
            }
        }

        fun getInstance(): GithubUserRepository {
            return INSTANCE
                ?: throw IllegalStateException("GithubUserRepository must be initialize")
        }
    }
}