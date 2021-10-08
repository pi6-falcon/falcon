package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.InvalidUserCredentialsException
import com.falcon.falcon.core.exception.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface AuthenticateUser {

    fun execute(request: User): User
}

@Service
class AuthenticateUseCase(
    private val userDataProvider: UserDataProvider,
    private val bCrypt: BCryptPasswordEncoder
) : AuthenticateUser {

    override fun execute(request: User): User =
        userDataProvider.findByUsername(request.username)?.let {
            if (it.username != request.username || !bCrypt.matches(request.password, it.password)) {
                throw InvalidUserCredentialsException()
            }
            it
        } ?: throw UserNotFoundException()
}
