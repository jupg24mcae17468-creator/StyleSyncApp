package com.example.stylesync.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("stylesync_session", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(prefs.getLong("user_id", -1L) != -1L)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun saveUserId(userId: Long) {
        prefs.edit().putLong("user_id", userId).apply()
        _isLoggedIn.value = true
    }

    fun getUserId(): Long = prefs.getLong("user_id", -1L)

    fun clear() {
        prefs.edit().clear().apply()
        _isLoggedIn.value = false
    }

    fun signOut() {
        clear()
    }

    fun setRememberMe(remember: Boolean) {
        prefs.edit().putBoolean("remember_me", remember).apply()
    }

    fun isRemembered(): Boolean = prefs.getBoolean("remember_me", false)
}
