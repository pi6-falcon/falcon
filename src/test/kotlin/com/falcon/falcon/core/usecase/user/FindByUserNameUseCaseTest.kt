package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
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
import org.springframework.security.core.userdetails.UsernameNotFoundException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindByUserNameUseCaseTest {

    private val userDataProvider: UserDataProvider = mockk()
    private val useCase: FindByUserNameUseCase = FindByUserNameUseCase(userDataProvider)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class LoadUserByUsername {

        @Test
        fun `Should return user successfully`() {
            // Given
            val user = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.findByUsername(user.username) } returns user
            // When
            val result = useCase.loadUserByUsername(user.username)
            // Then
            verify(exactly = 1) {
                userDataProvider.findByUsername(user.username)
            }
            result.username.shouldBe(user.username)
            result.password.shouldBe(user.password)
        }

        @Test
        fun `Should return exception if user does not exist`() {
            // Given
            val user = User("dummy-username", "dummy-password", UserType.PERMANENT)
            every { userDataProvider.findByUsername(user.username) } returns null
            // When-Then
            shouldThrowExactly<UsernameNotFoundException> {
                useCase.loadUserByUsername(user.username)
            }
        }
    }
}
