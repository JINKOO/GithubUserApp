package com.kjk.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GithubUserDatabaseDao {

    /**
     *  즐겨찾기 한 user를 db에 추가
     */
    @Insert
    suspend fun insertFavoriteUser(githubUserEntity: GithubUserEntity)


    /**
     *  keyword인 사용자 모두 fetch
     */
    @Query("SELECT * FROM user_table where name = :searchKeyword")
    fun getUsers(searchKeyword: String): LiveData<List<GithubUserEntity>>


    /**
     *  favorite에 추가한 사용자를 모두 fetch
     */
    @Query("SELECT * FROM user_table where isFavorite = 1")
    fun getFavoriteUsers(): LiveData<List<GithubUserEntity>>


    /**
     *  특정 사용자를
     *  즐겨찾기에서 해제
     */
    @Delete
    suspend fun deleteFavoriteUser(githubUserEntity: GithubUserEntity)
}