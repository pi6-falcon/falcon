package com.falcon.falcon.core.usecase.url.deleteshortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.exception.UrlNotFoundException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import io.kotest.assertions.throwables.shouldThrowExactly
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
class DeleteShortUrlUseCaseTest {

    private val urlDataProvider: UrlDataProvider = mockk()
    private val useCase: DeleteShortenedUrl = DeleteShortUrlUseCase(urlDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Delete {

        @Test
        fun `Should call delete() from provider if custom URL exists`() {
            // Given
            val shortUrl = "dummy-short-url"
            val longUrl = "dummy-long-url"
            val user = "dummy-user"
            val urlType = UrlType.CUSTOM
            val response = Url(shortUrl, longUrl, user, urlType)
            every { urlDataProvider.getByShortUrl(shortUrl) } returns response
            justRun { urlDataProvider.delete(response) }
            // When
            val result = useCase.execute(shortUrl)
            // Then
            verify(exactly = 1) {
                urlDataProvider.getByShortUrl(shortUrl)
                urlDataProvider.delete(response)
            }
            result.shouldBeTypeOf<Unit>()
        }

        @Test
        fun `Should throw UrlNotFoundException if URL does not exist`() {
            // Given
            val shortUrl = "dummy-short-url"
            every { urlDataProvider.getByShortUrl(shortUrl) } returns null
            // When-Then
            shouldThrowExactly<UrlNotFoundException> {
                useCase.execute(shortUrl)
            }
        }
    }
}