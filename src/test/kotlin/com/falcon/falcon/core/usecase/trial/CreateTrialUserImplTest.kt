package com.falcon.falcon.core.usecase.trial

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import io.kotest.matchers.date.shouldBeBetween
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import java.time.temporal.ChronoUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CreateTrialUserImplTest {

    private val createUserUseCase: CreateUserUseCase = mockk()
    private val trialDuration = 1L
    private val underTest: CreateTrialUserImpl = CreateTrialUserImpl(createUserUseCase, trialDuration)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `Should generate random user`() {
        // Given
        every { createUserUseCase.execute(any()) } returnsArgument 0
        // When
        val result = underTest.execute()
        // Then
        result.shouldBeTypeOf<User>()
        result.username.shouldBeUUID()
        result.password.shouldBeUUID()
        result.type.shouldBe(UserType.TRIAL)
        result.expirationDate?.shouldBeBetween(
            Instant.now().plus(50, ChronoUnit.MINUTES),
            Instant.now().plus(70, ChronoUnit.MINUTES),
        )
    }

}
