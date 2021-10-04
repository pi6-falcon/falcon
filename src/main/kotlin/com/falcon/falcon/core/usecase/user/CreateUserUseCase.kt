package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UserAlreadyFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service

interface CreateUser {

    fun execute(user: User): User
}

@Service
class CreateUserUseCase(private val userDataProvider: UserDataProvider) : CreateUser {

    override fun execute(user: User): User {
        if (userDataProvider.isUserAlreadyCreated(user)) {
            throw UserAlreadyFoundException()
        }

        return userDataProvider.save(user)
    }
}
