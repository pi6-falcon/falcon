package com.falcon.falcon.core.exception.resolver

import com.falcon.falcon.core.exception.InvalidUserCredentialsException
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.core.exception.UrlNotFoundException
import com.falcon.falcon.core.exception.UserAlreadyFoundException
import com.falcon.falcon.core.exception.UserNotFoundException
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoreExceptionResolverTest {

    private val resolver = CoreExceptionResolver()

    @Test
    fun `Should resolve ShortUrlAlreadyExistsException and return HttpStatus CONFLICT with message`() {
        // When
        val result = resolver.resolveShortUrlAlreadyExistsException(ShortUrlAlreadyExistsException())
        // Then
        result.body!!.message.shouldNotBeNull()
        result.body!!.code.shouldBe(HttpStatus.CONFLICT.value())
        result.statusCode.shouldBe(HttpStatus.CONFLICT)
        result.body!!.timestamp.shouldNotBeNull()
        result.body!!.errors.shouldBeEmpty()
    }

    @Test
    fun `Should resolve UrlNotFoundException and return HttpStatus NOT_FOUND with message`() {
        // When
        val result = resolver.resolveUrlNotFoundException(UrlNotFoundException())
        // Then
        result.body!!.message.shouldNotBeNull()
        result.body!!.code.shouldBe(HttpStatus.NOT_FOUND.value())
        result.statusCode.shouldBe(HttpStatus.NOT_FOUND)
        result.body!!.timestamp.shouldNotBeNull()
        result.body!!.errors.shouldBeEmpty()
    }

    @Test
    fun `Should resolve UserAlreadyFoundException and return HttpStatus CONFLICT with message`() {
        // When
        val result = resolver.resolveUserAlreadyFoundException(UserAlreadyFoundException())
        // Then
        result.body!!.message.shouldNotBeNull()
        result.body!!.code.shouldBe(HttpStatus.CONFLICT.value())
        result.statusCode.shouldBe(HttpStatus.CONFLICT)
        result.body!!.timestamp.shouldNotBeNull()
        result.body!!.errors.shouldBeEmpty()
    }

    @Test
    fun `Should resolve InvalidUserCredentialsException and return HttpStatus CONFLICT with message`() {
        // When
        val result = resolver.resolveInvalidUserCredentialsException(InvalidUserCredentialsException())
        // Then
        result.body!!.message.shouldNotBeNull()
        result.body!!.code.shouldBe(HttpStatus.UNAUTHORIZED.value())
        result.statusCode.shouldBe(HttpStatus.UNAUTHORIZED)
        result.body!!.timestamp.shouldNotBeNull()
        result.body!!.errors.shouldBeEmpty()
    }

    @Test
    fun `Should resolve UserNotFoundException and return HttpStatus NOT_FOUND with message`() {
        // When
        val result = resolver.resolveUserNotFoundException(UserNotFoundException())
        // Then
        result.body!!.message.shouldNotBeNull()
        result.body!!.code.shouldBe(HttpStatus.NOT_FOUND.value())
        result.statusCode.shouldBe(HttpStatus.NOT_FOUND)
        result.body!!.timestamp.shouldNotBeNull()
        result.body!!.errors.shouldBeEmpty()
    }
}
