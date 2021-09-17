package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface CreteUser {

    fun createUser(user: User) : User
}

@Service
class CreateUserUseCase(private val userDataProvider: UserDataProvider) : CreteUser {

    private val log = KotlinLogging.logger {}

    override fun createUser(user: User) = userDataProvider.saveUser(user)

}
