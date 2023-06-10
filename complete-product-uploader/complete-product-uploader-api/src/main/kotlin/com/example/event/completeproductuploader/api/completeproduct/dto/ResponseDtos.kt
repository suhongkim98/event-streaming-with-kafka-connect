package com.example.event.completeproductuploader.api.completeproduct.dto

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct

data class ResponseCompleteProductDto(
    val productId: String,
    val mallId: String,
    val title: String,
    val content: String,
    val price: String,
    val imageUrl: String,
) {
    companion object {
        fun of(completeProduct: CompleteProduct): ResponseCompleteProductDto {
            return ResponseCompleteProductDto(
                productId = completeProduct.productId,
                mallId = completeProduct.mallId,
                title = completeProduct.title,
                content = completeProduct.content,
                price = completeProduct.price,
                imageUrl = completeProduct.imageUrl,
            )
        }
    }
}