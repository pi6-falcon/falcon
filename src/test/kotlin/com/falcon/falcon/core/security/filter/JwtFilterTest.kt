package com.falcon.falcon.core.security.filter

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.filter.JwtFilter
import com.falcon.falcon.core.security.JwtUtils
import com.falcon.falcon.core.security.UserDetailsImpl
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.context.SecurityContextHolder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtFilterTest {

    private val jwtUtils: JwtUtils = mockk()
    private val findByUserNameUse: FindByUserNameUseCase = mockk()
    private val request: HttpServletRequest = spyk()
    private val response: HttpServletResponse = spyk()
    private val chain: FilterChain = spyk()
    private val jwtFilter: JwtFilter = JwtFilter(jwtUtils, findByUserNameUse)

    @BeforeEach
    fun init() {
        SecurityContextHolder.getContext().authentication = null
        clearAllMocks()
    }

    @Nested
    inner class DoFilterInternal {

        @Test
        fun `Should get a valid token from header and add the user to the SecurityContextHolder`() {
            // Given
            val token = "Bearer 1234"
            val tokenWithoutBearer = "1234"
            val username = "dummy-username"
            val password = "dummy-password"
            val user = UserDetailsImpl(User(username, password, UserType.PERMANENT))
            every { request.getHeader(AUTHORIZATION) } returns token
            every { jwtUtils.getUsernameFromToken(tokenWithoutBearer) } returns username
            every { findByUserNameUse.loadUserByUsername(username) } returns user
            // When
            jwtFilter.doFilter(request, response, chain)
            // Then
            SecurityContextHolder.getContext().authentication.name.shouldBe(user.username)
            verify(exactly = 1) {
                jwtUtils.getUsernameFromToken(tokenWithoutBearer)
                findByUserNameUse.loadUserByUsername(username)
            }
        }

        @Test
        fun `Should get a invalid token from header and skip filter`() {
            // Given
            val invalidToken = "1234"
            every { request.getHeader(AUTHORIZATION) } returns invalidToken
            // When
            jwtFilter.doFilter(request, response, chain)
            // Then
            SecurityContextHolder.getContext().authentication.shouldBeNull()
            verify(exactly = 0) {
                jwtUtils.getUsernameFromToken(any())
                findByUserNameUse.loadUserByUsername(any())
            }
        }

        @Test
        fun `Should skip entire filter if header is not present`() {
            // Given
            every { request.getHeader(AUTHORIZATION) } returns null
            // When
            jwtFilter.doFilter(request, response, chain)
            // Then
            SecurityContextHolder.getContext().authentication.shouldBeNull()
            verify(exactly = 0) {
                jwtUtils.getUsernameFromToken(any())
                findByUserNameUse.loadUserByUsername(any())
            }
        }
    }
}
