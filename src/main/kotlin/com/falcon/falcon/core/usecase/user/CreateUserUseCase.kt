package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.exception.user.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

interface CreateUser {

    @Throws(UserNotFoundException::class)
    fun createUser(user: User) : User
}

@Service
class CreateUserUseCase(private val userDataProvider: UserDataProvider) : CreateUser {

    @Throws(UserNotFoundException::class)
    override fun createUser(user: User) : User = userDataProvider.getUser(user).let {
        if (it != null)
            userDataProvider.saveUser(user)
        else
            throw UserNotFoundException()
    }
}
