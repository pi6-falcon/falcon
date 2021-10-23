package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UserAlreadyFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface CreateUserUseCase {

    fun execute(request: User): User
}

@Service
class CreateUserUseCaseImpl(
    private val userDataProvider: UserDataProvider,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : CreateUserUseCase {

    override fun execute(request: User): User {
        if (userDataProvider.isUserAlreadyCreated(request)) {
            throw UserAlreadyFoundException()
        }

        return userDataProvider.save(
            User(
                username = request.username,
                password = bCryptPasswordEncoder.encode(request.password),
                type = request.type,
                expirationDate = request.expirationDate
            )
        )
    }
}
