package com.kjk.githubuserapp.domain

/**
 *  UI controller에서 사용하기 위한 VO class
 */
data class GithubUserVO(
    val idNumber: Long,
    val profileImageUrl: String,
    val name: String,
    var isFavorite: Boolean = false
)