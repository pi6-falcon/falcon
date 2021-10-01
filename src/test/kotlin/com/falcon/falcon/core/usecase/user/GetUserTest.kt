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
class GetUserTest {

    @RelaxedMockK
    lateinit var userDataProviderImpl: UserDataProviderImpl

    private lateinit var getUser: GetUser

    private val user: User = User(
        username = "test",
        password = "123"
    )

    @Test
    @DisplayName("get user exist test")
    fun userExistTest() {

        getUser = GetUserUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns user

        assertDoesNotThrow { getUser.getUser(user) }
    }

    @Test
    @DisplayName("get user not exist test")
    fun userNotExistTest() {

        getUser = GetUserUseCase(userDataProviderImpl)

        every { userDataProviderImpl.getUser(user) } returns null

        assertThrows<UserNotFoundException> { getUser.getUser(user) }
    }
}