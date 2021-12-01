package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.CreationUtils
import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import com.falcon.falcon.core.usecase.user.DeleteUserUseCase
import com.falcon.falcon.core.usecase.user.UpdateUserPasswordUseCase
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private val createUser: CreateUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserPasswordUseCase = mockk();
    private val deleteUserUseCase: DeleteUserUseCase = mockk();
    private val userController: UserController = UserController(createUser, updateUserUseCase, deleteUserUseCase)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class CreateUser {

        @Test
        fun `Should create user successfully`() {
            // Given
            val request = CreationUtils.buildUserRequest("teste", "123")
            val response = User("teste", "123", UserType.PERMANENT)
            every { createUser.execute(any()) } returns response
            // When
            val result = userController.createUser(request)
            // Then
            verify(exactly = 1) { createUser.execute(any()) }
            result.statusCode.shouldBe(HttpStatus.CREATED)
            result.body!!.username.isNotBlank()
            result.body!!.createdAt.shouldNotBeNull()
            result.shouldBeTypeOf<ResponseEntity<UserResponse>>()
        }
    }
}
