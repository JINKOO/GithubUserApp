package com.kjk.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *  Room DAO interface
 */
@Dao
interface GithubUserDatabaseDao {

    /**
     *  즐겨찾기 한 user를 db에 추가
     */
    @Insert
    suspend fun insertFavoriteUser(githubUserEntity: GithubUserEntity)


    /**
     *  network로 받은 userdata를 local에 저장한다.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<GithubUserEntity>)


    /**
     *  keyword인 사용자 모두 fetch
     */
    @Query("SELECT * FROM user_table WHERE name = :searchKeyword")
    fun getResultFavoriteUsers(searchKeyword: String): LiveData<List<GithubUserEntity>>


    /**
     *  favorite에 추가한 사용자를 모두 fetch
     */
    @Query("SELECT * FROM user_table WHERE isFavorite = 1 ORDER BY name")
    fun getFavoriteUsers(): LiveData<List<GithubUserEntity>>


    /**
     *  특정 사용자를
     *  즐겨찾기에서 해제
     */
    @Delete
    suspend fun deleteFavoriteUser(githubUserEntity: GithubUserEntity)
}