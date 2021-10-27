package com.falcon.falcon.core.filter

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.trial.CreateTrialToken
import com.falcon.falcon.core.usecase.trial.CreateTrialUser
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrialUserFilterTest {

    private val createTrialUser: CreateTrialUser = mockk()
    private val createTrialToken: CreateTrialToken = mockk()
    private val underTest: TrialUserFilter = spyk(TrialUserFilter(createTrialUser, createTrialToken))
    private val request: HttpServletRequest = spyk()
    private val response: HttpServletResponse = spyk()
    private val chain: FilterChain = spyk()

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class DoFilterInternal {
        @Test
        fun `Should skip filter if header is not sent`() {
            // Given
            val user = User("dummy-username", "dummy-password")
            every { request.getHeader("Temporary-User") } returns null
            // When
            underTest.doFilter(request, response, chain)
            // Then
            verify(exactly = 0) {
                createTrialUser.execute()
                createTrialToken.execute(user)
            }
        }

        @Test
        fun `Should skip filter if header is present but is not temporary`() {
            // Given
            val user = User("dummy-username", "dummy-password")
            every { request.getHeader("Temporary-User") } returns "false"
            // When
            underTest.doFilter(request, response, chain)
            // Then
            verify(exactly = 0) {
                createTrialUser.execute()
                createTrialToken.execute(user)
            }
        }

        @Test
        fun `Should run filter if header is present and is true`() {
            // Given
            val user = User("dummy-username", "dummy-password")
            every { underTest.createUrlPath.matches(request) } returns true
            every { request.getHeader("Temporary-User") } returns "true"
            every { createTrialUser.execute() } returns user
            every { createTrialToken.execute(user) } returns ""
            // When
            underTest.doFilter(request, response, chain)
            // Then
            verify(exactly = 1) {
                createTrialUser.execute()
                createTrialToken.execute(user)
            }
        }
    }
}
