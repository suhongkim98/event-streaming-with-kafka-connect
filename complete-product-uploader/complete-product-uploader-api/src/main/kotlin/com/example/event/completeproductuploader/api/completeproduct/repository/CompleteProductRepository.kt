package com.example.event.completeproductuploader.api.completeproduct.repository

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CompleteProductRepository : MongoRepository<CompleteProduct, String> {
    fun findByProductId(productId: String)
    override fun findAll(pageable: Pageable): Page<CompleteProduct>
}