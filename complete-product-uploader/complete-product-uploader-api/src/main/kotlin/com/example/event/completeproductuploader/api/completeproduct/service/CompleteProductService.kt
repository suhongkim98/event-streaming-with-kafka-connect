package com.example.event.completeproductuploader.api.completeproduct.service

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import com.example.event.completeproductuploader.api.completeproduct.dto.RequestUpsertCompleteProductDto
import com.example.event.completeproductuploader.api.completeproduct.repository.CompleteProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CompleteProductService @Autowired constructor(
    val completeProductRepository: CompleteProductRepository,
) {
    fun upsertProduct(requestUpsertProductDto: RequestUpsertCompleteProductDto) {
        completeProductRepository.save(CompleteProduct.from(requestUpsertProductDto))
    }

    fun deleteProduct(productId: String) {
        completeProductRepository.deleteById(productId)
    }

    fun findAllCompleteProductByPageable(pageable: Pageable): Page<CompleteProduct> {
        return completeProductRepository.findAll(pageable)
    }
}