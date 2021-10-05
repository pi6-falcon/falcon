package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UserAlreadyFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateUserUseCaseTest {

    private val userDataProvider: UserDataProvider = mockk()
    private val useCase: CreateUserUseCase = CreateUserUseCase(userDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class CreateUser {

        @Test
        fun `Should create new user successfully`() {
            // Given
            val request = User("dummy-username", "dummy-password")
            val response = User("dummy-username", "dummy-password")
            every { userDataProvider.isUserAlreadyCreated(request) } returns false
            every { userDataProvider.save(request) } returns response
            // When
            val result = useCase.execute(request)
            // Then
            result.shouldBe(response)
        }

        @Test
        fun `Should return UserAlreadyFoundException if user already exists`() {
            // Given
            val request = User("dummy-username", "dummy-password")
            every { userDataProvider.isUserAlreadyCreated(request) } returns true
            // When-Then
            shouldThrowExactly<UserAlreadyFoundException> {
                useCase.execute(request)
            }
        }
    }
}
