package com.example.event.completeproductuploader.api.completeproduct.dto

data class RequestUpsertCompleteProductDto(
    val productId: String,
    val mallId: String,
    val title: String,
    val content: String,
    val price: String,
    val imageUrl: String,
)