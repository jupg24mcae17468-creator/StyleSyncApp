package com.example.stylesync.model

import java.util.Date

object FakeData {
    val stylists = listOf(
        StylistModel(
            id = 1,
            name = "Emma Thompson",
            photoUrl = "https://example.com/emma.jpg",
            specialization = "Hair Styling",
            description = "Expert in modern hair styling with 8 years of experience"
        ),
        StylistModel(
            id = 2,
            name = "John Davis",
            photoUrl = "https://example.com/john.jpg",
            specialization = "Coloring Specialist",
            description = "Color expert with a passion for transformation"
        ),
        StylistModel(
            id = 3,
            name = "Sarah Wilson",
            photoUrl = "https://example.com/sarah.jpg",
            specialization = "Makeup Artist",
            description = "Professional makeup artist for all occasions"
        )
    )

    val services = listOf(
        ServiceModel(
            id = 1,
            name = "Haircut",
            price = 45.0,
            duration = 45
        ),
        ServiceModel(
            id = 2,
            name = "Hair Spa",
            price = 75.0,
            duration = 90
        ),
        ServiceModel(
            id = 3,
            name = "Shave",
            price = 25.0,
            duration = 30
        ),
        ServiceModel(
            id = 4,
            name = "Makeup",
            price = 85.0,
            duration = 60
        )
    )

    private val tomorrow = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
    private val dayAfterTomorrow = Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000)

    val appointments = mutableListOf(
        AppointmentModel(
            id = 1,
            customerName = "Alice Brown",
            serviceType = "Haircut",
            appointmentDateTime = tomorrow,
            stylistName = "Emma Thompson"
        ),
        AppointmentModel(
            id = 2,
            customerName = "Bob Wilson",
            serviceType = "Hair Spa",
            appointmentDateTime = dayAfterTomorrow,
            stylistName = "John Davis"
        )
    )
}
