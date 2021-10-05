package com.falcon.falcon.core.exception.resolver

import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.core.exception.UrlNotFoundException
import java.time.LocalDateTime
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
class CoreExceptionResolver {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(ShortUrlAlreadyExistsException::class)
    fun resolveShortUrlAlreadyExistsException(e: ShortUrlAlreadyExistsException): ResponseEntity<ErrorResponse> {
        log.error { e.message }
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), arrayListOf(), e.message))
    }

    @ExceptionHandler(UrlNotFoundException::class)
    fun resolveUrlNotFoundException(e: UrlNotFoundException): ResponseEntity<ErrorResponse> {
        log.error { e.message }
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), arrayListOf(), e.message))
    }
}

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val code: Int,
    val errors: List<String>,
    val message: String,
)

