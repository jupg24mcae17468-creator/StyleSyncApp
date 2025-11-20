package com.example.stylesync.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylesync.data.UserPreferencesRepository
import com.example.stylesync.data.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserPreferencesRepository(application)
    private val sessionManager = SessionManager(application)

    val userName: StateFlow<String> = repository.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val userGender: StateFlow<String> = repository.userGender
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val preferredStylist: StateFlow<String> = repository.preferredStylist
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val notificationEnabled: StateFlow<Boolean> = repository.notificationEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    val profilePictureUri: StateFlow<String> = repository.profilePictureUri
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val loyaltyPoints: StateFlow<String> = repository.loyaltyPoints
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "0")

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    fun updateUserName(name: String) {
        viewModelScope.launch {
            repository.updateUserName(name)
        }
    }

    fun updateUserGender(gender: String) {
        viewModelScope.launch {
            repository.updateUserGender(gender)
        }
    }

    fun updatePreferredStylist(stylistName: String) {
        viewModelScope.launch {
            repository.updatePreferredStylist(stylistName)
        }
    }

    fun updateNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateNotificationEnabled(enabled)
        }
    }

    fun updateProfilePictureUri(uri: String) {
        viewModelScope.launch {
            repository.updateProfilePictureUri(uri)
        }
    }

    fun updateLoyaltyPoints(points: String) {
        viewModelScope.launch {
            repository.updateLoyaltyPoints(points)
        }
    }

    fun startEditing() {
        _isEditing.value = true
    }

    fun stopEditing() {
        _isEditing.value = false
    }

    suspend fun signOut() {
        // Clear all user preferences
        repository.updateUserName("")
        repository.updateUserGender("")
        repository.updatePreferredStylist("")
        repository.updateNotificationEnabled(true)
        repository.updateProfilePictureUri("")
        repository.updateLoyaltyPoints("0")

        // Update session state
        sessionManager.signOut()
    }

    // Add this to check login state
    val isLoggedIn = sessionManager.isLoggedIn.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )
}
