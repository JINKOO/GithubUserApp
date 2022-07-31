package com.kjk.githubuserapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class GithubUserEntity(
    @PrimaryKey
    var idNumber: Long,
    var profileImageUrl: String,
    var name: String,
    var isFavorite: Boolean
)