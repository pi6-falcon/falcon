package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User;
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service

interface AuthUser {

    fun validateUser(user: User) : User

}

@Service
class AuthUseCase(private val userDataProvider: UserDataProvider) : AuthUser {

    override fun validateUser(user: User): User = userDataProvider.getUser(user).let {
        if(it.username == user.username && it.password == user.password) it
        else throw RuntimeException("BAD DATA OF THE USER REQUEST")
    }

}
