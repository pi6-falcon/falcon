package com.falcon.falcon.core.security.utils

import com.falcon.falcon.core.security.JwtUtils
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.util.stream.Stream
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtUtilsTest {

    private val jwtUtils = JwtUtils("dummy-secret", "60000")

    @Nested
    inner class GenerateToken {

        @Test
        fun `Should generate valid token`() {
            val result = jwtUtils.generateToken("")
            result.shouldNotBeNull()
        }
    }

    @Nested
    inner class GetUsernameFromToken {

        @Test
        fun `Should return the subject of token if token is valid`() {
            // Given
            val username = "username"
            val token = jwtUtils.generateToken(username)
            // When
            val result = jwtUtils.getUsernameFromToken(token)
            // Then
            result.shouldBe(username)
        }

        @Test
        fun `Should return null if token is invalid`() {
            // Given
            val token = jwtUtils.generateToken("")
            // When
            val result = jwtUtils.getUsernameFromToken(token)
            // Then
            result.shouldBeNull()
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestTokenProvider::class)
    fun `Should validate token correctly`(candidate: String, expectedError: Boolean) {
        jwtUtils.isTokenValid(candidate).shouldBe(expectedError)
    }
}

private class TestTokenProvider : ArgumentsProvider {

    private val jwtUtils = JwtUtils("dummy-secret", "60000")

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments>? =
        Stream.of(
            Arguments.of("", false),
            Arguments.of("   ", false),
            Arguments.of("abcdefg123456", false),
            Arguments.of(jwtUtils.generateToken("teste"), true),
            Arguments.of(jwtUtils.generateToken(" "), false),
        )
}
