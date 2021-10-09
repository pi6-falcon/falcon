package com.falcon.falcon.configuration.validation

import io.kotest.matchers.types.shouldBeInstanceOf
import org.apache.commons.validator.routines.UrlValidator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlValidationTest {

    private val urlValidation: UrlValidation = UrlValidation()

    @Test
    fun `Should return an instance of UrlValidation`() {
        // When
        val result = urlValidation.urlValidator()
        // Then
        result.shouldBeInstanceOf<UrlValidator>()
    }
}
