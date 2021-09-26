package com.falcon.falcon.common.exception.resolver

import com.falcon.falcon.entrypoint.rest.exception.resolver.ErrorResponse
import java.time.LocalDateTime
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
class GlobalExceptionResolver {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun resolveMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = arrayListOf<String>()
        for (i in e.bindingResult.allErrors.indices) {
            e.bindingResult.allErrors[i].defaultMessage?.let { errors.add(it) }
        }
        val response = ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors, "invalid input")
        log.error { errors }
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun resolveHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        log.error { e.message }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), arrayListOf("invalid_input"), "input is invalid"))
    }
}

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val code: Int,
    val errors: List<String>,
    val message: String,
)