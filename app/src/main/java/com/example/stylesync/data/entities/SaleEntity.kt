package com.example.stylesync.data.entities

data class SaleEntity(
    val id: Long = 0,
    val appointmentId: Long? = null,
    val itemsJson: String = "",
    val totalCents: Int = 0,
    val paymentType: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
