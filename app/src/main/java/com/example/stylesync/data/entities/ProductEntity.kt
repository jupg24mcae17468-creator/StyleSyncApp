package com.example.stylesync.data.entities

data class ProductEntity(
    val id: Long = 0,
    val name: String,
    val priceCents: Int,
    val stock: Int = 0
)
