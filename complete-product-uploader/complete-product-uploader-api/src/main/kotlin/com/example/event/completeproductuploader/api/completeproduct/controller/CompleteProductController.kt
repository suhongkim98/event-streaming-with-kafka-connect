package com.example.event.completeproductuploader.api.completeproduct.controller

import com.example.event.completeproductuploader.api.completeproduct.CommonResponse
import com.example.event.completeproductuploader.api.completeproduct.service.CompleteProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CompleteProductController @Autowired constructor(
    val completeProductService: CompleteProductService,
) {
    @GetMapping("/api/v1/complete-products")
    suspend fun findAllCompleteProduct(): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse(
                message = "success",
                data = completeProductService.findAllCompleteProduct(),
            ), HttpStatus.OK
        )
    }

    @GetMapping("/api/v1/complete-products/{productId}")
    suspend fun findCompleteProductByProductId(@PathVariable("productId") productId: String): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse(
                message = "success",
                data = completeProductService.findCompleteProductByProductId(productId),
            ), HttpStatus.OK
        )
    }

    @DeleteMapping("/api/v1/complete-products/{productId}")
    suspend fun deleteCompleteProductByProductId(@PathVariable("productId") productId: String): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse(
                message = "success",
                data = completeProductService.deleteProduct(productId),
            ), HttpStatus.OK
        )
    }
}