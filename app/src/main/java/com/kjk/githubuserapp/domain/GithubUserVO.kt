package com.kjk.githubuserapp.domain

data class UserEntity(
    val profileImageUrl: String,
    val name: String,
    var isFavorite: Boolean = false
)