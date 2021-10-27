package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.exception.InvalidUserCredentialsException
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticateUserTest {


    private val bCryptPasswordEncoder: BCryptPasswordEncoder = mockk()
    private val userDataProvider: UserDataProvider = mockk()
    private val useCase: AuthenticateUser = AuthenticateUseCase(userDataProvider, bCryptPasswordEncoder)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Authenticate {

        @Test
        fun `Should return user if it is authenticated successfully`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            val response = User("dummy-username", "dummy-password", UserType.PERMANENT)
            val expectedResponse = User("dummy-username", "dummy-password", UserType.PERMANENT)

            every { bCryptPasswordEncoder.matches(request.password, any()) } returns true
            every { userDataProvider.findByUsername(request.username) } returns response
            // When
            val result = useCase.execute(request)
            // Then
            verify(exactly = 1) { userDataProvider.findByUsername(request.username) }
            result.shouldBe(expectedResponse)
        }

        @Test
        fun `Should throw InvalidUserCredentialsException when credentials does not match`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            val response = User("different-username", "different-password", UserType.PERMANENT)

            every { bCryptPasswordEncoder.matches(request.password, any()) } returns false
            every { userDataProvider.findByUsername(request.username) } returns response
            // When-Then
            shouldThrowExactly<InvalidUserCredentialsException> {
                useCase.execute(request)
            }
            verify(exactly = 1) { userDataProvider.findByUsername(request.username) }
        }

        @Test
        fun `Should throw UserNotFoundException when dataProvider returns null`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.findByUsername(request.username) } returns null
            // When-Then
            shouldThrowExactly<UserNotFoundException> {
                useCase.execute(request)
            }
            verify(exactly = 1) { userDataProvider.findByUsername(request.username) }
        }
    }
}
