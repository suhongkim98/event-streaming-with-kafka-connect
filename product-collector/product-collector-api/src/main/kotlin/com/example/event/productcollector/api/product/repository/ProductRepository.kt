package com.example.event.productcollector.api.product.repository

import com.example.event.productcollector.api.product.domain.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ProductRepository : ReactiveMongoRepository<Product, String> {
    suspend fun findByProductId(productId: String)
}