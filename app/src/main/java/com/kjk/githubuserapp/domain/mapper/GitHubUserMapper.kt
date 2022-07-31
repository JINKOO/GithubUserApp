package com.kjk.githubuserapp.domain.mapper

import com.kjk.githubuserapp.data.local.GithubUserEntity
import com.kjk.githubuserapp.domain.GithubUserVO

fun GithubUserVO.toGitHubUserEntity(): GithubUserEntity {
    return GithubUserEntity(
        idNumber, profileImageUrl, name, isFavorite
    )
}