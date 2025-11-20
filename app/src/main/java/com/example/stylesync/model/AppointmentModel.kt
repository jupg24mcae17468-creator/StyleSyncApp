package com.example.stylesync.model

import java.util.Date

data class AppointmentModel(
    val id: Int,
    val customerName: String,
    val serviceType: String,
    val appointmentDateTime: Date,
    val stylistName: String
)

data class StylistModel(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val specialization: String,
    val description: String
)

data class ServiceModel(
    val id: Int,
    val name: String,
    val price: Double,
    val duration: Int // in minutes
)
