package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.entity.User
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
    private val useCase = spyk(RandomShortUrlUseCase(urlDataProvider, 1))

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
            val user = User("dummy-user", "dummy-password")
            val expectedRequest = Url(longUrl = "dummy-long-url", shortUrl = generatedShortUrl, userIdentifier = user.username, expirationDate = null)

            every { urlDataProvider.getAllUrlsByUserIdentifier(user.username) } returns emptyList()
            every { urlDataProvider.save(expectedRequest) } returns expectedRequest
            every { useCase.generateUniqueShortUrl() } returns generatedShortUrl
            // When
            val result = useCase.execute(request, user)
            // Then
            verify(exactly = 1) {
                useCase.generateUniqueShortUrl()
                urlDataProvider.save(expectedRequest)
            }

            result.shouldBe(expectedRequest)
        }

        @Test
        fun `If random URL already exists, should regenerate and try to save again`() {
            // Given
            val generatedShortUrl = "abcdefg"
            val request = Url(longUrl = "dummy-long-url")
            val user = User("dummy-user", "dummy-password")
            val expectedRequest = Url(longUrl = "dummy-long-url", shortUrl = generatedShortUrl, type = UrlType.RANDOM, userIdentifier = user.username, expirationDate = null)

            every { urlDataProvider.getAllUrlsByUserIdentifier(user.username) } returns emptyList()
            every { useCase.generateUniqueShortUrl() } returns generatedShortUrl
            every { urlDataProvider.urlAlreadyExists(generatedShortUrl) } returns true andThen false
            every { urlDataProvider.save(expectedRequest) } returns expectedRequest
            // When
            val result = useCase.execute(request, user)
            // Then
            verify(exactly = 1) {
                useCase.generateUniqueShortUrl()
            }

            verify(exactly = 1) { urlDataProvider.save(expectedRequest) }

            result.shouldBe(expectedRequest)
        }
    }

    @Nested
    inner class GenerateShortUrl {

        @Test
        fun `Should returns a 6 (six) length string`() {
            // Given
            every { urlDataProvider.urlAlreadyExists(any()) } returns false
            // When
            val result = useCase.generateUniqueShortUrl()
            // Then
            result.length.shouldBeExactly(6)
            result.shouldBeTypeOf<String>()
        }
    }
}
