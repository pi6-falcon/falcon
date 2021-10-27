package com.falcon.falcon.core.usecase.trial

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.security.JwtUtils
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CreateTrialTokenImplTest {

    private val jwtUtils: JwtUtils = mockk()
    private val trialDuration = 1L
    private val createTrialToken: CreateTrialTokenImpl = CreateTrialTokenImpl(jwtUtils, trialDuration)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }
    @Test
    fun `Should call jwtUtils generateToken method`() {
        // Given
        val user = User("dummy-username", "dummy-password")
        val token = "abcedfg"
        every { jwtUtils.generateToken(user.username, trialDuration) } returns token
        // When
        val result = createTrialToken.execute(user)
        // Then
        result.shouldBe(token)
    }
}
