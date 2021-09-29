package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.CreationUtils
import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.usecase.url.deleteshortenurl.DeleteShortenedUrl
import com.falcon.falcon.core.usecase.url.shortenurl.ShortenUrl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlControllerTest {

    private val randomShortUrlUseCase: ShortenUrl = mockk()
    private val customShortUrlUseCase: ShortenUrl = mockk()
    private val deleteShortenedUrlUseCase: DeleteShortenedUrl = mockk()
    private val controller = UrlController(randomShortUrlUseCase, customShortUrlUseCase, deleteShortenedUrlUseCase)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class ShortenToRandomUrl {
        @Test
        fun `Should call toDomain() before sending to the use case`() {
            val request = CreationUtils.buildRandomShortenUrlRequest()
            val response = CreationUtils.buildShortenUrlResponse()
            // Arrange
            every { randomShortUrlUseCase.shorten(request.toDomain()) } returns Url(shortUrl = response.shortUrl)
            // Act
            val r = controller.shortenToRandomUrl(request)
            // Assert
            verify(exactly = 1) {
                randomShortUrlUseCase.shorten(request.toDomain())
            }
        }

        @Test
        fun `Should call toResponse() before returning to user`() {

        }

        @Test
        fun `Should return ResponseEntity of ShortenUrlResponse with HttpStatus CREATED`() {

        }
    }
}
