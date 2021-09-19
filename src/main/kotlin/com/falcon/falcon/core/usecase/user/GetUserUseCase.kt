package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface GetUser {

    fun getUser(user: User) : User
}

@Service
class GetUserUseCase(private val userDataProvider: UserDataProvider) : GetUser {

    private val log = KotlinLogging.logger {}

    override fun getUser(user: User) = userDataProvider.getUser(user)

}
