package com.falcon.falcon.entrypoint.rest.exception.resolver

import java.time.LocalDateTime
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
class RestExceptionResolver {

    private val log = KotlinLogging.logger {}
}

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val code: Int,
    val errors: List<String>,
    val message: String,
)
