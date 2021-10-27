package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.exception.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindUserUseCaseUseCaseTest {

    private val userDataProvider: UserDataProvider = mockk()
    private val useCase: FindUserUseCase = FindUserUseCaseImpl(userDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class LoadUserByUsername {

        @Test
        fun `Should return User successfully`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            val response = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.findByUsername(request.username) } returns response
            // When
            val result = useCase.execute(request)
            // Then
            verify(exactly = 1) {
                userDataProvider.findByUsername(request.username)
            }
            result.shouldBe(response)
        }

        @Test
        fun `Should throw UserNotFoundException if user does not exist`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.findByUsername(request.username) } returns null
            // When-Then
            shouldThrowExactly<UserNotFoundException> {
                useCase.execute(request)
            }
        }
    }
}
