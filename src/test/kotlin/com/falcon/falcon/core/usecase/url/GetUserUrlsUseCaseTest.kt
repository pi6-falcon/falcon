package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.usecase.url.shortenurl.RandomShortUrlUseCase
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetUserUrlsUseCaseTest {

    private val urlDataProvider: UrlDataProvider = mockk()
    private val useCase = GetUserUrlsUseCaseImpl(urlDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Invoke {

        @Test
        fun `Get list of urls`() {

            val userIdentifier = "test"

            every { urlDataProvider.getAllUrlsByUserIdentifier(any()) } returns
                    arrayListOf(Url("test", "test", "test", UrlType.RANDOM, Instant.now()))

            val result = useCase.invoke(userIdentifier)

            result.size.shouldBe(1)
        }

        @Test
        fun `Get empty list of urls`() {

            val userIdentifier = "test"

            every { urlDataProvider.getAllUrlsByUserIdentifier(any()) } returns
                    emptyList()

            val result = useCase.invoke(userIdentifier)

            result.shouldBeEmpty()
        }

    }
}