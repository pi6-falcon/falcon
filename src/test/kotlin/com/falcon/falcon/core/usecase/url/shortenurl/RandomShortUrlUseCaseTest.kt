package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomShortUrlUseCaseTest {

    private val urlDataProvider: UrlDataProvider = mockk()
    private val useCase = spyk(RandomShortUrlUseCase(urlDataProvider))

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Shorten {

        @Test
        fun `Should save the random URL successfully`() {
            // Given
            val generatedShortUrl = "abcdefg"
            val request = Url(longUrl = "dummy-long-url", type = UrlType.RANDOM)
            val expectedRequest = Url(longUrl = "dummy-long-url", shortUrl = generatedShortUrl)
            every { urlDataProvider.urlAlreadyExists(generatedShortUrl) } returns false
            every { urlDataProvider.save(expectedRequest) } returns expectedRequest
            every { useCase.generateShortUrl() } returns generatedShortUrl
            // When
            val result = useCase.shorten(request)
            // Then
            verify(exactly = 1) {
                useCase.generateShortUrl()
                urlDataProvider.urlAlreadyExists(generatedShortUrl)
                urlDataProvider.save(expectedRequest)
            }

            result.shouldBe(expectedRequest)
        }

        @Test
        fun `If random URL already exists, should regenerate and try to save again`() {
            // Given
            val generatedShortUrl = "abcdefg"
            val request = Url(longUrl = "dummy-long-url")
            val expectedRequest = Url(longUrl = "dummy-long-url", shortUrl = generatedShortUrl, type = UrlType.RANDOM)
            every { useCase.generateShortUrl() } returns generatedShortUrl
            every { urlDataProvider.urlAlreadyExists(generatedShortUrl) } returns true andThen false
            every { urlDataProvider.save(expectedRequest) } returns expectedRequest
            // When
            val result = useCase.shorten(request)
            // Then
            verify(exactly = 2) {
                useCase.generateShortUrl()
                urlDataProvider.urlAlreadyExists(generatedShortUrl)
            }

            verify(exactly = 1) { urlDataProvider.save(expectedRequest) }

            result.shouldBe(expectedRequest)
        }
    }

    @Nested
    inner class GenerateShortUrl {

        @Test
        fun `Should returns a 6 (six) length string`() {
            // When
            val result = useCase.generateShortUrl()
            // Then
            result.length.shouldBeExactly(6)
            result.shouldBeTypeOf<String>()
        }
    }
}
