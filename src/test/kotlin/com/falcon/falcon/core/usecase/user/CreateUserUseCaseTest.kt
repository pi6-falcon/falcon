package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.exception.UserAlreadyFoundException
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
class CreateUserUseCaseTest {

    private val userDataProvider: UserDataProvider = mockk()
    private val bCryptPasswordEncoder: BCryptPasswordEncoder = mockk()
    private val useCase: CreateUserUseCase = CreateUserUseCaseImpl(userDataProvider, bCryptPasswordEncoder)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class CreateUser {

        @Test
        fun `Should create new user successfully`() {
            // Given
            val username = "dummy-username"
            val password = "dummy-password"
            val encryptedPassword = "encrypted-dummy-password"
            val request = User(username, password, UserType.PERMANENT)
            val expectedUser = User(username, encryptedPassword, UserType.PERMANENT)
            every { userDataProvider.isUserAlreadyCreated(request) } returns false
            every { userDataProvider.save(expectedUser) } returns expectedUser
            every { bCryptPasswordEncoder.encode(password) } returns encryptedPassword
            // When
            val result = useCase.execute(request)
            // Then
            verify(exactly = 1) {
                bCryptPasswordEncoder.encode(password)
                userDataProvider.save(expectedUser)
            }

            result.shouldBe(expectedUser)
        }

        @Test
        fun `Should return UserAlreadyFoundException if user already exists`() {
            // Given
            val request = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.isUserAlreadyCreated(request) } returns true
            // When-Then
            shouldThrowExactly<UserAlreadyFoundException> {
                useCase.execute(request)
            }
        }
    }
}
