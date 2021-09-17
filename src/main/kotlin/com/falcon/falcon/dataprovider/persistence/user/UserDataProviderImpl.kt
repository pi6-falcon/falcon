package com.falcon.falcon.dataprovider.persistence.user

import mu.KotlinLogging
import org.springframework.stereotype.Service
import com.falcon.falcon.core.entity.User

interface UserDataProvider {

    fun saveUser(user: User)

}

@Service
class UserDataProviderImpl(private val repository: UserRepository) : UserDataProvider {

    private val log = KotlinLogging.logger {}

    override fun saveUser(user: User) {
        log.info { "Saving user in db..." }

            repository.save(UserEntity(
                username = user.username,
                password = user.password
            ))

        log.info { "User was save in db..." }
    }

}
