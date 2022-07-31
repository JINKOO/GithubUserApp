package com.kjk.githubuserapp.data.mapper

import com.kjk.githubuserapp.data.local.GithubUserEntity
import com.kjk.githubuserapp.data.remote.response.UserResponse
import com.kjk.githubuserapp.domain.GithubUserVO


/**
 * network response object -> domain object
 */
fun List<UserResponse>.toGithubUserVOList(): List<GithubUserVO> {
    return map {
        GithubUserVO(
            idNumber = it.idNumber,
            profileImageUrl = it.userProfileUrl,
            name = it.loginId
        )
    }
}


/**
 *  database object -> domain object
 */
fun GithubUserEntity.toGithubUserVO(): GithubUserVO {
    return GithubUserVO(
        idNumber,
        profileImageUrl,
        name,
        isFavorite
    )
}

fun List<GithubUserEntity>.toGithubUserFavoriteVOList(): List<GithubUserVO> {
    return map {
        GithubUserVO(
            idNumber = it.idNumber,
            profileImageUrl = it.profileImageUrl,
            name = it.name,
            isFavorite = it.isFavorite
        )
    }
}