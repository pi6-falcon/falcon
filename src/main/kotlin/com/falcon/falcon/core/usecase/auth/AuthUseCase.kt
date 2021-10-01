package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User;
import com.falcon.falcon.core.usecase.exception.auth.UserDataNotValidException
import com.falcon.falcon.core.usecase.exception.user.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

interface AuthUser {

    @Throws(UserNotFoundException::class, UserDataNotValidException::class)
    fun validateUser(user: User): User
}

@Service
class AuthUseCase(private val userDataProvider: UserDataProvider) : AuthUser {

    @Throws(UserNotFoundException::class, UserDataNotValidException::class)
    override fun validateUser(user: User): User = userDataProvider.getUser(user).let {
        if (it == null) throw UserNotFoundException()
        else {
            if (it.username == user.username && it.password == user.password) it
            else throw UserDataNotValidException()
        }
    }
}
