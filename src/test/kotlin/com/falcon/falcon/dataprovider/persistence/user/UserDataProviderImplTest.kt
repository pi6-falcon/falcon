package com.falcon.falcon.dataprovider.persistence.user

import com.falcon.falcon.core.entity.User
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataProviderImplTest {

    private val userRepository: UserRepository = mockk()
    private val userDataProvider: UserDataProvider = UserDataProviderImpl(userRepository)

    @Nested
    inner class Save {

        @Test
        fun `Should convert before sending to repository and returning`() {
            // Given
            val request = User("dummy-user", "dummy-password")
            val expectedRequest = UserEntity("dummy-user", "dummy-password")
            val expectedResponse = User("dummy-user", "dummy-password")
            every { userRepository.save(expectedRequest) } returns expectedRequest
            // When
            val result = userDataProvider.save(request)
            // Then
            verify(exactly = 1) { userRepository.save(expectedRequest) }
            result.shouldBe(expectedResponse)
        }
    }

    @Nested
    inner class FindByUsername {

        @Test
        fun `Should return user successfully`() {
            // Given
            val username = "dummy-username"
            val response = UserEntity(username, "dummy-password")
            val expectedResponse = User(username, "dummy-password")
            every { userRepository.findByUsername(username) } returns response
            // When
            val result = userDataProvider.findByUsername(username)
            // Then
            verify(exactly = 1) { userRepository.findByUsername(username) }
            result.shouldBe(expectedResponse)
        }

        @Test
        fun `Should return null if user does not exist`() {
            // Given
            val username = "dummy-username"
            every { userRepository.findByUsername(username) } returns null
            // When
            val result = userDataProvider.findByUsername(username)
            // Then
            result.shouldBeNull()
        }
    }

    @Nested
    inner class IsUserAlreadyCreated {

        @Test
        fun `Should return true if user is already created`() {
            // Given
            val request = User("dummy-username", "dummy-password")
            every { userRepository.existsById(request.username) } returns true
            // When
            val result = userDataProvider.isUserAlreadyCreated(request)
            // Then
            result.shouldBeTrue()
        }

        @Test
        fun `Should return false if user isnt created`() {
            // Given
            val request = User("dummy-username", "dummy-password")
            every { userRepository.existsById(request.username) } returns false
            // When
            val result = userDataProvider.isUserAlreadyCreated(request)
            // Then
            result.shouldBeFalse()
        }
    }
}
