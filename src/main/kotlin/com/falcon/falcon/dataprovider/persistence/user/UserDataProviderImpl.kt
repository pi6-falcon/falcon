package com.falcon.falcon.dataprovider.persistence.user

import mu.KotlinLogging
import org.springframework.stereotype.Service
import com.falcon.falcon.core.entity.User

interface UserDataProvider {

    fun saveUser(request: User) : User

}

@Service
class UserDataProviderImpl(private val repository: UserRepository) : UserDataProvider {

    private val log = KotlinLogging.logger {}

    override fun saveUser(request: User) : User {
        log.info { "Saving user in db..." }

            val user = repository.save(UserEntity(
                username = request.username,
                password = request.password
            ))

        log.info { "User was save in db..." }

        return User(
            username = user.username,
            password = user.password
        )
    }

}
