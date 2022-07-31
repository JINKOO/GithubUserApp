package com.kjk.githubuserapp.data.remote.network

import com.kjk.githubuserapp.data.remote.response.ResponseFromNetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface GitHubUserApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") searchName: String,
        @Query("sort") sort: String = "",
        @Query("order") order: String = "",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): ResponseFromNetwork
}


object GithubUserApi {
    val gitHubUserApiService: GitHubUserApiService by lazy {
        retrofit.create(GitHubUserApiService::class.java)
    }
}