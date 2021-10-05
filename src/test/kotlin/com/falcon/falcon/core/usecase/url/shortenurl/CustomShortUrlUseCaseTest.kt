package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomShortUrlUseCaseTest {

    private val urlDataProvider: UrlDataProvider = mockk()
    private val useCase: UrlShortener = CustomShortUrlUseCase(urlDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Shorten {

        @Test
        fun `Should save the custom URL successfully`() {
            // Given
            val customUrl = "this-is-the-custom-url"
            val longUrl = "dummy-long-url"
            val request = Url(shortUrl = customUrl, longUrl = longUrl, type = UrlType.CUSTOM)
            val expectedRequest = Url(shortUrl = customUrl, longUrl = longUrl, type = UrlType.CUSTOM)
            every { urlDataProvider.urlAlreadyExists(customUrl) } returns false
            every { urlDataProvider.save(expectedRequest) } returns expectedRequest
            // When
            val result = useCase.execute(request)
            // Then
            verify(exactly = 1) {
                urlDataProvider.urlAlreadyExists(customUrl)
                urlDataProvider.save(expectedRequest)
            }

            result.shouldBe(expectedRequest)
        }

        @Test
        fun `Should return exception if URL already exists`() {
            // Given
            val customUrl = "this-is-the-custom-url"
            val longUrl = "dummy-long-url"
            val request = Url(shortUrl = customUrl, longUrl = longUrl, type = UrlType.CUSTOM)
            every { urlDataProvider.urlAlreadyExists(customUrl) } returns true
            // When-Then
            shouldThrowExactly<ShortUrlAlreadyExistsException> {
                useCase.execute(request)
            }
        }
    }
}
