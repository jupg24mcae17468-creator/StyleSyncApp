package com.example.stylesync.data

import android.content.Context

@Deprecated("AppDatabaseProvider is deprecated — Room removed. Use UserStorage or in-memory providers")
object AppDatabaseProvider {
    @Deprecated("Do not call; placeholder for compatibility")
    fun getDatabase(context: Context): AppDatabase {
        return AppDatabase
    }
}
