package com.kjk.githubuserapp.data.mapper

import com.kjk.githubuserapp.data.remote.response.UserResponse
import com.kjk.githubuserapp.domain.UserEntity

fun List<UserResponse>.toUserEntity(): List<UserEntity> {
    return map {
        UserEntity(
            profileImageUrl = it.userProfileUrl,
            name = it.loginId
        )
    }
}