package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.CreationUtils
import com.falcon.falcon.ValidateBeans
import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.usecase.url.deleteshortenurl.DeleteShortenedUrl
import com.falcon.falcon.core.usecase.url.shortenurl.ShortenUrl
import com.falcon.falcon.validate
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import java.util.stream.Stream
import javax.validation.Validation
import javax.validation.Validator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlControllerTest {

    private val randomShortUrlUseCase: ShortenUrl = mockk()
    private val customShortUrlUseCase: ShortenUrl = mockk()
    private val deleteShortenedUrlUseCase: DeleteShortenedUrl = mockk()
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    private val controller = UrlController(randomShortUrlUseCase, customShortUrlUseCase, deleteShortenedUrlUseCase)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class ShortenToRandomUrl {

        @Test
        fun `Should convert then return ResponseEntity of ShortenUrlResponse with HttpStatus CREATED`() {
            // Given
            val request = CreationUtils.buildRandomShortenUrlRequest()
            val expectedResponse = CreationUtils.buildShortenUrlResponse()
            every { randomShortUrlUseCase.shorten(request.toDomain()) } returns Url(shortUrl = expectedResponse.shortUrl)
            // When
            val response = controller.shortenToRandomUrl(request)
            // Then
            verify(exactly = 1) { randomShortUrlUseCase.shorten(request.toDomain()) }
            response.statusCode.shouldBe(HttpStatus.CREATED)
            response.body!!.shortUrl.shouldBe(expectedResponse.shortUrl)
            response.shouldBeTypeOf<ResponseEntity<ShortenUrlResponse>>()
        }
    }

    @Nested
    inner class CustomToRandomUrl {

        @Test
        fun `Should convert then return ResponseEntity of ShortenUrlResponse with HttpStatus CREATED`() {
            // Given
            val request = CreationUtils.buildCustomShortenUrlRequest()
            val expectedResponse = CreationUtils.buildShortenUrlResponse()
            every { customShortUrlUseCase.shorten(request.toDomain()) } returns Url(shortUrl = expectedResponse.shortUrl)
            // When
            val response = controller.shortenToCustomUrl(request)
            // Then
            verify(exactly = 1) { customShortUrlUseCase.shorten(request.toDomain()) }
            response.statusCode.shouldBe(HttpStatus.CREATED)
            response.body!!.shortUrl.shouldBe(expectedResponse.shortUrl)
            response.shouldBeTypeOf<ResponseEntity<ShortenUrlResponse>>()
        }
    }

    @Nested
    inner class DeleteShortenedLongUrl {

        @Test
        fun `Should convert then return ResponseEntity of Void with HttpStatus NO_CONTENT`() {
            // Given
            val request = "this-is-a-shortened-url"
            justRun { deleteShortenedUrlUseCase.delete(request) }
            // When
            val response = controller.deleteShortenedLongUrl(request)
            // Then
            verify(exactly = 1) { deleteShortenedUrlUseCase.delete(request) }
            response.statusCode.shouldBe(HttpStatus.NO_CONTENT)
            response.body.shouldBeNull()
            response.shouldBeTypeOf<ResponseEntity<Void>>()
        }
    }

    @ParameterizedTest
    @ArgumentsSource(RandomShortenUrlRequestProvider::class)
    fun `Should validate RandomShortenUrlRequest correctly`(candidate: ValidateBeans<RandomShortenUrlRequest>) {
        candidate.input.validate().size.shouldBeExactly(candidate.errorsExpected)
    }

    @ParameterizedTest
    @ArgumentsSource(CustomShortenUrlRequestProvider::class)
    fun `Should validate CustomShortenUrlRequest correctly`(candidate: ValidateBeans<CustomShortenUrlRequest>) {
        candidate.input.validate().size.shouldBeExactly(candidate.errorsExpected)
    }
}

private class RandomShortenUrlRequestProvider : ArgumentsProvider {

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments>? =
        Stream.of(
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest("www.google.com.br"), 0)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest("http://www.google.com.br"), 0)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest("https://www.google.com.br"), 0)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest(longUrl = null), 1)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest(longUrl = ""), 1)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest(longUrl = "   "), 1)),
            Arguments.of(ValidateBeans(CreationUtils.buildRandomShortenUrlRequest(longUrl = "invalid-url"), 0)), // Invalid URLs are being validated on core layer!
        )
}

private class CustomShortenUrlRequestProvider : ArgumentsProvider {

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments>? =
        Stream.of(
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("https://www.google.com.br", "random-url"), 0)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest(null, "random-url"), 1)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("https://www.google.com.br", null), 1)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest(null, null), 2)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("", ""), 3)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("   ", "     "), 2)),
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("https://www.google.com.br", "a"), 1)), // min
            Arguments.of(ValidateBeans(CreationUtils.buildCustomShortenUrlRequest("https://www.google.com.br", "this-url-have-more-than-20-characters"), 1)), // max
        )
}

