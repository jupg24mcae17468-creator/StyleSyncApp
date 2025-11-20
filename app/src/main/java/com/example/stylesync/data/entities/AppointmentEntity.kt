package com.example.stylesync.data.entities

import java.util.Date

data class AppointmentEntity(
    val id: Long = 0,
    val userId: Long,
    val stylistId: Long,
    val serviceId: Long,
    val dateTimeMillis: Long,
    val durationMinutes: Int,
    val status: String = "PENDING",
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
