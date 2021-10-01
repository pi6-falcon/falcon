package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.exception.auth.UserDataNotValidException
import com.falcon.falcon.core.usecase.exception.user.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProviderImpl
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AuthUserTest {

    @RelaxedMockK
    lateinit var userDataProviderImpl: UserDataProviderImpl

    private lateinit var authUser: AuthUser

    private val user: User = User(
        username = "test",
        password = "123"
    )

    @Test
    @DisplayName("validate user not exist test")
    fun userNotExistTest() {

        authUser = AuthUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns null

        assertThrows<UserNotFoundException> { authUser.validateUser(user) }
    }

    @Test
    @DisplayName("validate user exist not match username test")
    fun userExistNotMatchUsernameTest() {

        authUser = AuthUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns User("zxcv", "123")

        assertThrows<UserDataNotValidException> { authUser.validateUser(user) }
    }

    @Test
    @DisplayName("validate user exist not match password test")
    fun userExistNotMatchPasswordTest() {

        authUser = AuthUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns User("test", "321")

        assertThrows<UserDataNotValidException> { authUser.validateUser(user) }
    }

    @Test
    @DisplayName("validate user exist test")
    fun userExistMatchUsernameAndPasswordTest() {

        authUser = AuthUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns user

        assertDoesNotThrow { authUser.validateUser(user) }
    }
}