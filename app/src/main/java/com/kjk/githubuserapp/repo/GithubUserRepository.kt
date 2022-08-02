package com.kjk.githubuserapp.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kjk.githubuserapp.data.local.GithubUserDatabase
import com.kjk.githubuserapp.data.local.GithubUserEntity
import com.kjk.githubuserapp.data.mapper.toGithubUserFavoriteVOList
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

    fun getFavoriteUserFromLocal(): LiveData<List<GithubUserVO>> {
        return Transformations.map(database.githubUserDatabaseDao.getFavoriteUsers()) {
            it.toGithubUserFavoriteVOList()
        }
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