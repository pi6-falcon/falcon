package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UserAlreadyFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface CreateUser {

    fun execute(request: User): User
}

@Service
class CreateUserUseCase(
    private val userDataProvider: UserDataProvider,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : CreateUser {

    override fun execute(request: User): User {
        if (userDataProvider.isUserAlreadyCreated(request)) {
            throw UserAlreadyFoundException()
        }

        request.apply {
            password = bCryptPasswordEncoder.encode(password)
        }

        return userDataProvider.save(request)
    }
}
