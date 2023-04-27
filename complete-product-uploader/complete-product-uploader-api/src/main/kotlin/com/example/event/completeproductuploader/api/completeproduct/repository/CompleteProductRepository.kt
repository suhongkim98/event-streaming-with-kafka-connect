package com.example.event.completeproductuploader.api.completeproduct.repository

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import org.springframework.data.mongodb.repository.MongoRepository

interface CompleteProductRepository : MongoRepository<CompleteProduct, String> {
    fun findByProductId(productId: String)
}