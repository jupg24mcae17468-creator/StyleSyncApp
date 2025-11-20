package com.example.stylesync.data.entities

data class UserEntity(
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String? = null,
    val hashedPassword: String,
    val loyaltyPoints: Int = 0,
    val isAdmin: Boolean = false
)
