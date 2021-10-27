package com.falcon.falcon.entrypoint.rest.auth

import com.falcon.falcon.CreationUtils
import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.security.JwtUtils
import com.falcon.falcon.core.usecase.auth.AuthenticateUseCase
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    private val authenticateUser: AuthenticateUseCase = mockk()

    private val jwtUtils: JwtUtils = mockk()

    private val bCrypt: BCryptPasswordEncoder = mockk()

    private val authController: AuthController = AuthController(authenticateUser, jwtUtils)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Post {

        @Test
        fun `Get token`() {

            val request = CreationUtils.buildAuthRequest()

            val executeResponse = User("teste", "123", UserType.PERMANENT)

            every { bCrypt.encode(any()) } returns "123"

            every { authenticateUser.execute(any()) } returns executeResponse

            every { jwtUtils.generateToken(executeResponse.username) } returns "123"

            val result = authController.authenticateUser(request)

            verify(exactly = 1) { authenticateUser.execute(any()) }
            result.statusCode.shouldBe(HttpStatus.OK)
            result.body!!.token.isNotBlank()
            result.body!!.createdAt.shouldNotBeNull()
            result.shouldBeTypeOf<ResponseEntity<AuthResponse>>()

        }
    }
}
