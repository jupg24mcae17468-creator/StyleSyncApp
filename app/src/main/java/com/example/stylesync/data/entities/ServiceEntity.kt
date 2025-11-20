package com.example.stylesync.data.entities

data class ServiceEntity(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val priceCents: Int = 0,
    val durationMinutes: Int = 0,
    val category: String = ""
)
