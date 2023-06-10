package com.example.event.completeproductuploader.api.completeproduct.controller

import com.example.event.completeproductuploader.api.completeproduct.CommonResponse
import com.example.event.completeproductuploader.api.completeproduct.service.CompleteProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CompleteProductController @Autowired constructor(
    val completeProductService: CompleteProductService,
) {
    @GetMapping("/api/v1/complete-products")
    fun findAllCompleteProductByPageable(pageable: Pageable): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse(
                message = "success",
                data = completeProductService.findAllCompleteProductByPageable(pageable),
            ), HttpStatus.OK
        )
    }

    @GetMapping("/api/v1/complete-products/{productId}")
    fun findCompleteProductByProductId(@PathVariable("productId") productId: String): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse(
                message = "success",
                data = completeProductService.findCompleteProductByProductId(productId),
            ), HttpStatus.OK
        )
    }
}