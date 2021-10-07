package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.CreationUtils
import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import com.falcon.falcon.core.usecase.user.FindUserUseCaseImpl
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

    private val getUser: FindUserUseCaseImpl = mockk()

    private val userController: UserController = UserController(createUser, getUser)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }


    @Nested
    inner class Post {

        @Test
        fun `Create user`() {

            val request = CreationUtils.buildUserRequest("teste","123")

            val executeResponse = User("teste", "123")

            every { createUser.execute(any()) } returns executeResponse

            val result = userController.createUser(request)

            verify(exactly = 1) { createUser.execute(any()) }
            result.statusCode.shouldBe(HttpStatus.CREATED)
            result.body!!.username.isNotBlank()
            result.body!!.createdAt.shouldNotBeNull()
            result.shouldBeTypeOf<ResponseEntity<UserResponse>>()
        }
    }

    @Nested
    inner class Get {

        @Test
        fun `Get user`() {

            val request = CreationUtils.buildUserRequest("teste","123")

            val executeResponse = User("teste", "123")

            every { getUser.execute(any()) } returns executeResponse

            val result = userController.getUser(request)

            verify(exactly = 1) { getUser.execute(any()) }
            result.statusCode.shouldBe(HttpStatus.OK)
            result.body!!.username.isNotBlank()
            result.body!!.createdAt.shouldNotBeNull()
            result.shouldBeTypeOf<ResponseEntity<UserResponse>>()
        }
    }
}