package com.example.stylesync.data

import android.content.Context
import com.example.stylesync.data.entities.UserEntity
import com.example.stylesync.model.FakeData

/**
 * Lightweight Repository for the reverted stable baseline.
 * - Uses UserStorage (SharedPreferences + Gson) for user persistence
 * - Exposes read-only access to fake/stubbed data (stylists/services/appointments)
 */
@Suppress("StaticFieldLeak", "unused")
class Repository private constructor(context: Context) {

    // store only the application context to avoid leaks
    private val appContext: Context = context.applicationContext
    private val userStorage = UserStorage(appContext)

    // User operations
    fun getUserByEmail(email: String): UserEntity? = userStorage.getUserByEmail(email)
    fun insertUser(user: UserEntity): Long = userStorage.insertUser(user)

    // Read-only access to demo data
    fun getStylists() = FakeData.stylists
    fun getServices() = FakeData.services
    fun getAppointments() = FakeData.appointments

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context): Repository {
            return INSTANCE ?: synchronized(this) {
                val instance = Repository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }

    // No-op preseed for this simplified baseline (FakeData already seeded in-memory)
    fun preseedIfEmpty(context: Context) {
        // Intentionally left empty
    }
}
