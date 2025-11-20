package com.example.stylesync.data.entities

data class StylistEntity(
    val id: Long = 0,
    val name: String,
    val experienceYears: Int = 0,
    val specialties: String = "",
    val rating: Float = 0f
)
