package com.falcon.falcon.common.exception.resolver

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import javax.validation.ConstraintViolationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlobalExceptionResolverTest {

    private val resolver: GlobalExceptionResolver = GlobalExceptionResolver()

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `Should handle MethodArgumentNotValidException returning the errors and HttpStatus BAD_REQUEST`() {
        // Given
        val methodParameter: MethodParameter = mockk()
        val bindingResult: BindingResult = spyk()
        val errors: List<ObjectError> = listOf(
            ObjectError("first-object", "first-message"),
            ObjectError("second-object", ""),
            ObjectError("third-message", null, null, null)
        )
        every { bindingResult.allErrors } returns errors
        // When
        val result = resolver.resolveMethodArgumentNotValidException(MethodArgumentNotValidException(methodParameter, bindingResult))
        // Then
        result.body!!.errors.size.shouldBe(2)
        result.body!!.errors[0].shouldBe("first-message")
        result.body!!.errors[1].shouldBeEmpty()
        result.body!!.code.shouldBe(HttpStatus.BAD_REQUEST.value())
        result.body!!.timestamp.shouldNotBeNull()
        result.statusCode.shouldBe(HttpStatus.BAD_REQUEST)
        result.shouldBeTypeOf<ResponseEntity<ErrorResponse>>()
    }

    @Test
    fun `Should handle MessageNotReadableException returning mocked String and HttpStatus BAD_REQUEST`() {
        // Given
        val message = "dummy-error"
        val exception = HttpMessageNotReadableException(message)
        // When
        val result = resolver.resolveHttpMessageNotReadableException(exception)
        // Then
        result.body!!.errors[0].shouldBe("invalid_input")
        result.body!!.message.shouldNotBe(message)
        result.body!!.code.shouldBe(HttpStatus.BAD_REQUEST.value())
        result.body!!.timestamp.shouldNotBeNull()
        result.statusCode.shouldBe(HttpStatus.BAD_REQUEST)
        result.shouldBeTypeOf<ResponseEntity<ErrorResponse>>()
    }

    @Test
    fun `Should handle ConstraintViolationException returning the errors and HttpStatus BAD_REQUEST`() {
        // Given
        val exception = ConstraintViolationException(mutableSetOf(spyk()))
        every { exception.constraintViolations.first().message } returns "dummy-message"
        // When
        val result = resolver.resolveConstraintViolationException(exception)
        // Then
        result.body!!.errors.size.shouldBe(1)
        result.body!!.errors.first().shouldBe("dummy-message")
        result.body!!.code.shouldBe(HttpStatus.BAD_REQUEST.value())
        result.body!!.timestamp.shouldNotBeNull()
        result.statusCode.shouldBe(HttpStatus.BAD_REQUEST)
        result.shouldBeTypeOf<ResponseEntity<ErrorResponse>>()
    }
}
