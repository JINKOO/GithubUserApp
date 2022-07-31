package com.kjk.githubuserapp.domain

data class GithubUserVO(
    val idNumber: Long,
    val profileImageUrl: String,
    val name: String,
    var isFavorite: Boolean = false
)