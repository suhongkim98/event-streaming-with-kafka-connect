package com.example.event.productcollector.api.product.controller

import com.example.event.productcollector.api.product.dto.RequestUpsertProductDto
import com.example.event.productcollector.api.product.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController @Autowired constructor(
    val productService: ProductService
) {
    @PostMapping("/api/v1/products")
    suspend fun upsertProduct(@RequestBody requestUpsertProductDto: RequestUpsertProductDto): ResponseEntity<String> {

        productService.upsertProduct(requestUpsertProductDto)

        return ResponseEntity("UPSERT SUCCESS", HttpStatus.CREATED)
    }
}