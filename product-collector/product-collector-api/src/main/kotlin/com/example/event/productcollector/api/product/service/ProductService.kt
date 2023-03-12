package com.example.event.productcollector.api.product.service

import com.example.event.productcollector.api.product.domain.Product
import com.example.event.productcollector.api.product.dto.RequestUpsertProductDto
import com.example.event.productcollector.api.product.repository.ProductRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(
    val productRepository: ProductRepository
) {
    suspend fun upsertProduct(requestUpsertProductDto: RequestUpsertProductDto) {
        productRepository.save(Product.from(requestUpsertProductDto))
            .awaitSingle()
    }
}