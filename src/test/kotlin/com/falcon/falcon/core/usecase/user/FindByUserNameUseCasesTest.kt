package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.dataprovider.persistence.user.UserDataProviderImpl
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.userdetails.UsernameNotFoundException

@ExtendWith(MockKExtension::class)
class FindByUserNameUseCasesTest {

    @RelaxedMockK
    lateinit var userDataProviderImpl: UserDataProviderImpl

    private lateinit var findByUserNameUseCases: FindByUserNameUseCases

    private val username = "test"

    @Test
    @DisplayName("username not exist test")
    fun usernameNotExistTest() {

        findByUserNameUseCases = FindByUserNameUseCases(userDataProviderImpl)

        every { userDataProviderImpl.getByUserName(username) } returns null

        assertThrows<UsernameNotFoundException> { findByUserNameUseCases.loadUserByUsername(username) }
    }

    @Test
    @DisplayName("username exist test")
    fun usernameExistTest() {

        findByUserNameUseCases = FindByUserNameUseCases(userDataProviderImpl)

        every { userDataProviderImpl.getByUserName(username) } returns User("test", "123")

        assertDoesNotThrow { findByUserNameUseCases.loadUserByUsername(username) }
    }

}