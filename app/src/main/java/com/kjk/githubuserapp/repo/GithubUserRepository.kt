package com.kjk.githubuserapp.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kjk.githubuserapp.data.local.GithubUserDatabase
import com.kjk.githubuserapp.data.mapper.toGithubUserFavoriteVOList
import com.kjk.githubuserapp.data.mapper.toGithubUserVOList
import com.kjk.githubuserapp.data.remote.network.GithubUserApi
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.domain.mapper.toGitHubUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

/**
 *  Repository pattern을 적용하기 위한 class
 *  remote(network), local DB(Room)에서 data를 fetch한다.
 */
class GithubUserRepository(
    application: Application
) {

    private val database = GithubUserDatabase.getInstance(application)

    suspend fun getUsersFromNetwork(searchKeyword: String) = withContext(Dispatchers.IO) {
        GithubUserApi.gitHubUserApiService.searchUsers(searchKeyword).items
            .toGithubUserVOList()
            .sortedBy { it.name }
    }

    fun getFavoriteUserFromLocal(): LiveData<List<GithubUserVO>> {
        return Transformations.map(database.githubUserDatabaseDao.getFavoriteUsers()) { favoriteUsers ->
            favoriteUsers
                .sortedBy { it.name }
                .toGithubUserFavoriteVOList()
        }
    }

    fun getResultFavoriteUser(searchKeyword: String): LiveData<List<GithubUserVO>> {
        return Transformations.map(database.githubUserDatabaseDao.getResultFavoriteUsers(searchKeyword)) { favoriteUsers ->
            favoriteUsers
                .sortedBy { it.name }
                .toGithubUserFavoriteVOList()
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