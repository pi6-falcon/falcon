package com.falcon.falcon.security.filter

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import com.falcon.falcon.dataprovider.persistence.url.UrlEntity
import com.falcon.falcon.security.utils.JwtUtils
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtFilterTest {


    private val jwtUtils: JwtUtils = mockk()
    private val findByUserNameUse: FindByUserNameUseCase = mockk()
    private val request: HttpServletRequest = mockk()
    private val response: HttpServletResponse = mockk()
    private val chain: FilterChain = mockk()
    private val jwtFilter: JwtFilter = JwtFilter(jwtUtils, findByUserNameUse)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }


    @Nested
    inner class withHeader {
        @Test
        fun `Get jwt in header`() {
            // Given
            every { jwtFilter.doFilter(request, response, chain) } returns Unit
            // When
            val result = jwtFilter.doFilter(request, response, chain)
            // Then
            verify(exactly = 1) { jwtFilter.doFilter(request, response, chain) }
            result.shouldBeTypeOf<Unit>()
        }
    }
}