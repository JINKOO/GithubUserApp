package com.kjk.githubuserapp.data.remote.network

import com.kjk.githubuserapp.data.remote.response.ResponseFromNetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/search/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface GitHubUserApiService {
    @GET("user")
    suspend fun getAllUsers(
        @Header("accept") accept: String = "application/vnd.github+json",
//        @Header("Authorization:") auth: String = "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",
        @Query("q") searchName: String = "jinkoo",
        @Query("sort") sort: String = "followers",
        @Query("order") order: String = "asc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): ResponseFromNetwork
}

object GithubUserApi {
    val gitHubUserApiService: GitHubUserApiService by lazy {
        retrofit.create(GitHubUserApiService::class.java)
    }
}