package com.example.event.productcollector.api.product.domain

import com.example.event.productcollector.api.product.dto.RequestUpsertProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Product(
    @Id
    val productId: String,
    val mallId: String,
    val title: String,
    val content: String,
    val price: String,
    val imageUrl: String,
) {
    companion object Factory {
        fun from(requestUpsertProductDto: RequestUpsertProductDto): Product = Product(
            productId = requestUpsertProductDto.productId,
            mallId = requestUpsertProductDto.mallId,
            title = requestUpsertProductDto.title,
            content = requestUpsertProductDto.content,
            price = requestUpsertProductDto.price,
            imageUrl = requestUpsertProductDto.imageUrl,
        )
    }
}