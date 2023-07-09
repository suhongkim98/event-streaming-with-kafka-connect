package com.example.event.completeproductuploader.api.completeproduct.service

import com.example.event.completeproductuploader.api.completeproduct.domain.CompleteProduct
import com.example.event.completeproductuploader.api.completeproduct.dto.RequestUpsertCompleteProductDto
import com.example.event.completeproductuploader.api.completeproduct.dto.ResponseCompleteProductDto
import com.example.event.completeproductuploader.api.completeproduct.exception.NotFoundCompleteProductException
import com.example.event.completeproductuploader.api.completeproduct.repository.CompleteProductRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompleteProductService @Autowired constructor(
    val completeProductRepository: CompleteProductRepository,
) {
    suspend fun upsertProduct(requestUpsertProductDto: RequestUpsertCompleteProductDto) {
        completeProductRepository.save(CompleteProduct.from(requestUpsertProductDto))
            .awaitSingle()
    }

    suspend fun deleteProduct(productId: String) {
        completeProductRepository.deleteById(productId)
            .awaitSingle()
    }

    suspend fun findAllCompleteProduct(): List<ResponseCompleteProductDto> {
        // https://docs.spring.io/spring-framework/docs/5.2.0.M1/spring-framework-reference/languages.html#coroutines
        return completeProductRepository.findAll().asFlow().map {
            ResponseCompleteProductDto.of(it)
        }.toList()
    }

    suspend fun findCompleteProductByProductId(productId: String): ResponseCompleteProductDto {
        val completeProduct = completeProductRepository.findByProductId(productId)
            ?: throw NotFoundCompleteProductException()
        return ResponseCompleteProductDto.of(completeProduct)
    }
}