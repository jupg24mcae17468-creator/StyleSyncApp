package com.example.stylesync.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val PREFERRED_STYLIST = stringPreferencesKey("preferred_stylist")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val PROFILE_PICTURE_URI = stringPreferencesKey("profile_picture_uri")
        val LOYALTY_POINTS = stringPreferencesKey("loyalty_points")
    }

    val userName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_NAME] ?: ""
    }

    val userGender: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_GENDER] ?: ""
    }

    val preferredStylist: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.PREFERRED_STYLIST] ?: ""
    }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATION_ENABLED] ?: true
    }

    val profilePictureUri: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.PROFILE_PICTURE_URI] ?: ""
    }

    val loyaltyPoints: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LOYALTY_POINTS] ?: "0"
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
        }
    }

    suspend fun updateUserGender(gender: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_GENDER] = gender
        }
    }

    suspend fun updatePreferredStylist(stylistName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_STYLIST] = stylistName
        }
    }

    suspend fun updateNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = enabled
        }
    }

    suspend fun updateProfilePictureUri(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PROFILE_PICTURE_URI] = uri
        }
    }

    suspend fun updateLoyaltyPoints(points: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOYALTY_POINTS] = points
        }
    }
}
