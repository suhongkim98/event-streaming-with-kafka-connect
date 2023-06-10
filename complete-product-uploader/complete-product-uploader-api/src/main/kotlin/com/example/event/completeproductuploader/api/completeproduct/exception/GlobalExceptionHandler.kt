package com.example.event.completeproductuploader.api.completeproduct.exception

import com.example.event.completeproductuploader.api.completeproduct.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
     */
    @ExceptionHandler(value = [BindException::class, HttpMessageNotReadableException::class])
    protected fun handleBindException(): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.REQUEST_PARAMETER_BIND_FAILED
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }

    @ExceptionHandler(value = [NotFoundCompleteProductException::class])
    protected fun handleNotFoundCompleteProductException(): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.NOT_FOUND_COMPLETE_PRODUCT
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }

}