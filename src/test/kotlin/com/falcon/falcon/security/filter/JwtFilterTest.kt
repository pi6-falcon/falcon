package com.falcon.falcon.security.filter

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import com.falcon.falcon.security.impl.UserDetailsImpl
import com.falcon.falcon.security.utils.JwtUtils
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
import org.springframework.mock.web.MockFilterChain
import org.springframework.security.core.context.SecurityContextHolder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtFilterTest {

    private val jwtUtils: JwtUtils = mockk()
    private val findByUserNameUse: FindByUserNameUseCase = mockk()
    private val request: HttpServletRequest = spyk()
    private val response: HttpServletResponse = spyk()
    private val chain: FilterChain = MockFilterChain()
    private val jwtFilter: JwtFilter = JwtFilter(jwtUtils, findByUserNameUse)

    @BeforeEach
    fun init() {
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
            val user = UserDetailsImpl(User(username, password))
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
    }
}
