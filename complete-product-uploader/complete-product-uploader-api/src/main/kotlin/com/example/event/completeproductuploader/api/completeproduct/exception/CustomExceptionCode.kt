package com.example.event.completeproductuploader.api.completeproduct.exception

import org.springframework.http.HttpStatus

enum class CustomExceptionCode(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    REQUEST_PARAMETER_BIND_FAILED(HttpStatus.BAD_REQUEST, "REQ_001", "PARAMETER_BIND_FAILED"),
    NOT_FOUND_COMPLETE_PRODUCT(HttpStatus.NOT_FOUND, "REQ_002", "상품을 찾을 수 없음"),
}