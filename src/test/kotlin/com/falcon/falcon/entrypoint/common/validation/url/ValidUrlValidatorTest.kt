package com.falcon.falcon.entrypoint.common.validation.url

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.commons.validator.routines.UrlValidator
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidUrlValidatorTest {

    private val urlValidator: UrlValidator = mockk()
    private val validUrlValidator: ValidUrlValidator = ValidUrlValidator(urlValidator)

    @Nested
    inner class IsValid {
        @Test
        fun `Should call isValid() method from validator and return false`() {
            // Given
            val request = "wrong-url"
            every { urlValidator.isValid(request) } returns false
            // When
            val result = validUrlValidator.isValid(request, null)
            // Then
            verify(exactly = 1) { urlValidator.isValid(request) }
            result.shouldBeFalse()
        }

        @Test
        fun `Should call isValid() method from validator and return true`() {
            // Given
            val request = "https://www.google.com.br"
            every { urlValidator.isValid(request) } returns true
            // When
            val result = validUrlValidator.isValid(request, null)
            // Then
            verify(exactly = 1) { urlValidator.isValid(request) }
            result.shouldBeTrue()
        }
    }
}
