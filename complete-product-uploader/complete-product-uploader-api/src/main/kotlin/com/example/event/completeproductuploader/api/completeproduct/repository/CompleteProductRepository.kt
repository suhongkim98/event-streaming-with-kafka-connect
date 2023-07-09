package com.example.event.completeproductuploader.api.completeproduct.repository

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CompleteProductRepository : ReactiveMongoRepository<CompleteProduct, String> {
    suspend fun findByProductId(productId: String): CompleteProduct?
}