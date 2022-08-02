package com.kjk.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubUserEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase : RoomDatabase() {

    abstract val githubUserDatabaseDao: GithubUserDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        fun getInstance(context: Context): GithubUserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        GithubUserDatabase::class.java,
                        "user_table"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}