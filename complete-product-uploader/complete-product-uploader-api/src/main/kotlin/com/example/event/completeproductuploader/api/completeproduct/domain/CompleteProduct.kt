package com.example.event.completeproductuploader.api.completeproduct.domain

import com.example.event.completeproductuploader.api.completeproduct.dto.RequestUpsertCompleteProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class CompleteProduct(
    @Id
    val productId: String,
    val mallId: String,
    val title: String,
    val content: String,
    val price: String,
    val imageUrl: String,
) {
    companion object Factory {
        fun from(requestUpsertProductDto: RequestUpsertCompleteProductDto): CompleteProduct = CompleteProduct(
            productId = requestUpsertProductDto.productId,
            mallId = requestUpsertProductDto.mallId,
            title = requestUpsertProductDto.title,
            content = requestUpsertProductDto.content,
            price = requestUpsertProductDto.price,
            imageUrl = requestUpsertProductDto.imageUrl,
        )
    }
}