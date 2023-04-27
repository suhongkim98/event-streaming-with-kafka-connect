package com.example.event.completeproductuploader.api.completeproduct.service

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import com.example.event.completeproductuploader.api.completeproduct.dto.RequestUpsertCompleteProductDto
import com.example.event.completeproductuploader.api.completeproduct.repository.CompleteProductRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompleteProductService @Autowired constructor(
    val completeProductRepository: CompleteProductRepository
) {
    fun upsertProduct(requestUpsertProductDto: RequestUpsertCompleteProductDto) {
        completeProductRepository.save(CompleteProduct.from(requestUpsertProductDto))
    }

    fun deleteProduct(productId: String) {
        completeProductRepository.deleteById(productId)
    }
}