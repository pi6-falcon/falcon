package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.exception.user.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

interface GetUser {

    @Throws(UserNotFoundException::class)
    fun getUser(user: User): User
}

@Service
class GetUserUseCase(private val userDataProvider: UserDataProvider) : GetUser {

    @Throws(UserNotFoundException::class)
    override fun getUser(user: User) = userDataProvider.getUser(user) ?: throw UserNotFoundException()
}
