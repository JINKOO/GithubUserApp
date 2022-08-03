package com.kjk.githubuserapp.data.mapper

import com.kjk.githubuserapp.data.local.GithubUserEntity
import com.kjk.githubuserapp.data.remote.response.UserResponse
import com.kjk.githubuserapp.domain.GithubUserVO

/**
 *   remote, local, domain에서 사용하기 위해,
 *   각각 fetch한 data를 경우에 따라, 변환해주는 함수들 정의
 */


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