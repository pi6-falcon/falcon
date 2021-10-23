package com.falcon.falcon.dataprovider.persistence.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlDataProviderImplTest {

    private val repository: UrlRepository = mockk()
    private val provider: UrlDataProvider = UrlDataProviderImpl(repository)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Save {
        @Test
        fun `Should convert before sending to repository and returning`() {
            // Given
            val shortUrl = "dummy-url"
            val longUrl = "long-url"
            val user = "user"
            val urlType = UrlType.RANDOM
            val request = Url(shortUrl, longUrl, user, urlType, expirationDate = null)
            val expectedRequest = UrlEntity(shortUrl, longUrl, user, urlType)
            every { repository.save(expectedRequest) } returns expectedRequest
            // When
            val result = provider.save(request)
            // Then
            verify(exactly = 1) { repository.save(expectedRequest) }
            result.shouldBeTypeOf<Url>()
        }
    }

    @Nested
    inner class UrlAlreadyExists {
        @Test
        fun `Should return true if exists by ID`() {
            // Given
            val request = "dummy-url"
            every { repository.existsById(request) } returns true
            // When
            val result = provider.urlAlreadyExists(request)
            // Then
            verify(exactly = 1) { repository.existsById(request) }
            result.shouldBeTrue()
        }

        @Test
        fun `Should return false if does not exist by ID`() {
            // Given
            val request = "dummy-url"
            every { repository.existsById(request) } returns false
            // When
            val result = provider.urlAlreadyExists(request)
            // Then
            verify(exactly = 1) { repository.existsById(request) }
            result.shouldBeFalse()
        }
    }

    @Nested
    inner class Delete {
        @Test
        fun `Should convert before sending to repository and return Unit`() {
            // Given
            val shortUrl = "dummy-url"
            val longUrl = "long-url"
            val user = "user"
            val urlType = UrlType.RANDOM
            val request = Url(shortUrl, longUrl, user, urlType, expirationDate = null)
            val expectedRequest = UrlEntity(shortUrl, longUrl, user, urlType)
            justRun { repository.delete(expectedRequest) }
            // When
            val result = provider.delete(request)
            // Then
            verify(exactly = 1) { repository.delete(expectedRequest) }
            result.shouldBeTypeOf<Unit>()
        }
    }

    @Nested
    inner class GetByShortUrl {
        @Test
        fun `Should convert and return if url exists`() {
            // Given
            val shortUrl = "dummy-url"
            val longUrl = "long-url"
            val user = "user"
            val urlType = UrlType.RANDOM
            val response = UrlEntity(shortUrl, longUrl, user, urlType)
            val expectedResponse = Url(shortUrl, longUrl, user, urlType, expirationDate = null)
            every { repository.findByShortUrl(shortUrl) } returns response
            // When
            val result = provider.getByShortUrl(shortUrl)
            // Then
            verify(exactly = 1) { repository.findByShortUrl(shortUrl) }
            result.shouldBe(expectedResponse)
        }

        @Test
        fun `Should return null if url does not exist`() {
            // Given
            val shortUrl = "dummy-url"
            every { repository.findByShortUrl(shortUrl) } returns null
            // When
            val result = provider.getByShortUrl(shortUrl)
            // Then
            verify(exactly = 1) { repository.findByShortUrl(shortUrl) }
            result.shouldBe(null)
        }
    }
}
