package com.example.stylesync.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylesync.data.Repository
import com.example.stylesync.data.SessionManager
import com.example.stylesync.data.UserStorage
import com.example.stylesync.data.entities.UserEntity
import com.example.stylesync.util.HashUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = Repository.getInstance(application)
    private val session = SessionManager(application)
    private val userStorage = UserStorage(application)

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    fun signUp(name: String, email: String, password: String, rememberMe: Boolean, onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            // validation
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _authError.value = "Invalid email"
                return@launch
            }
            if (password.length < 6) {
                _authError.value = "Password too short"
                return@launch
            }
            val existing = userStorage.getUserByEmail(email)
            if (existing != null) {
                _authError.value = "User already exists"
                return@launch
            }

            val hashed = HashUtils.sha256(password)
            val id = userStorage.insertUser(
                UserEntity(name = name, email = email, hashedPassword = hashed)
            )
            session.saveUserId(id)
            session.setRememberMe(rememberMe)
            _authError.value = null
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun signIn(email: String, password: String, rememberMe: Boolean, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userStorage.getUserByEmail(email)
            if (user == null) {
                _authError.value = "No user found"
                return@launch
            }
            val hashed = HashUtils.sha256(password)
            if (user.hashedPassword != hashed) {
                _authError.value = "Invalid credentials"
                return@launch
            }
            session.saveUserId(user.id)
            session.setRememberMe(rememberMe)
            _authError.value = null
            // ensure onSuccess is invoked on the main thread
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}
