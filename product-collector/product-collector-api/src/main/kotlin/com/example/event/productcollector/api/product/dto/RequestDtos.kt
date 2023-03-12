package com.example.event.productcollector.api.product.dto

data class RequestUpsertProductDto(
    val productId: String,
    val mallId: String,
    val title: String,
    val content: String,
    val price: String,
    val imageUrl: String,
)