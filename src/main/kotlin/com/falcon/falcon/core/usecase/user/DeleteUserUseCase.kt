package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.exception.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.stereotype.Service

interface DeleteUserUseCase {

    fun execute(username: String)
}

@Service
class DeleteUserUseCaseImpl(
    private val userDataProvider: UserDataProvider
) : DeleteUserUseCase {

    override fun execute(username: String) {
        userDataProvider.findByUsername(username)?.let {
            userDataProvider.delete(it)
        } ?: throw UserNotFoundException()
    }
}
