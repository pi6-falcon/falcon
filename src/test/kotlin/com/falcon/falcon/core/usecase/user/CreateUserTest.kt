package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
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
class CreateUserTest {

    @RelaxedMockK
    lateinit var userDataProviderImpl: UserDataProviderImpl

    private lateinit var createUserUseCase: CreateUser

    private val user: User = User(
        username = "test",
        password = "123"
    )

    @Test
    @DisplayName("user not exist test")
    fun userNotExistTest() {

        createUserUseCase = CreateUserUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns user

        every { userDataProviderImpl.saveUser(user) } returns user

        assertDoesNotThrow { createUserUseCase.createUser(user) }
    }

    @Test
    @DisplayName("user exist test")
    fun userExistTest() {

        createUserUseCase = CreateUserUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns null

        assertThrows<UserNotFoundException> { createUserUseCase.createUser(user) }
    }

}