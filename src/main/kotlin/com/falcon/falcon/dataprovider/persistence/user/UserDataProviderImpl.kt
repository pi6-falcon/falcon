package com.falcon.falcon.dataprovider.persistence.user

import mu.KotlinLogging
import org.springframework.stereotype.Service
import com.falcon.falcon.core.entity.User
import kotlin.jvm.Throws

interface UserDataProvider {

    fun saveUser(request: User) : User

    fun getUser(request: User) : User

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

    override fun getUser(request: User): User {

        log.info { "Get user in db..." }

        val user = repository.findByUsername(request.username)
            ?: throw RuntimeException("Username doesn't exist")

        log.info { "Got user in db..." }

        return User(
            username = user.username,
            password = user.password
        )
    }

}
