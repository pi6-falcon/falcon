package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service

interface FindUserUseCase {

    fun execute(request: User): User
}

@Service
class FindUserUseCaseImpl(private val userDataProvider: UserDataProvider) : FindUserUseCase {

    override fun execute(request: User) = userDataProvider.findByUsername(request.username) ?: throw UserNotFoundException()
}
