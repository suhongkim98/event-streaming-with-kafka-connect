package com.example.event.completeproductuploader.api.completeproduct

data class ErrorResponse(
    val message: String,
)

data class CommonResponse(
    val message: String? = null,
    val data: Any? = null,
)