package com.example.stylesync.data.entities

data class LoyaltyRuleEntity(
    val id: Long = 0,
    val pointsPerDollar: Int = 1,
    val redeemThreshold: Int = 100,
    val redeemValueCents: Int = 0
)
