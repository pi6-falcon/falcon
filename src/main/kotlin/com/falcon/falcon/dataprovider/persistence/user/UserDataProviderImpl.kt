package com.falcon.falcon.dataprovider.persistence.user

import com.falcon.falcon.core.entity.User
import org.springframework.stereotype.Service

interface UserDataProvider {

    fun saveUser(request: User): User

    fun getUser(request: User): User?

    fun getByUserName(userName: String): User?

}

@Service
class UserDataProviderImpl(private val repository: UserRepository) : UserDataProvider {

    override fun saveUser(request: User): User = repository.save(
        UserEntity(
            username = request.username,
            password = request.password
        )
    ).let {
        User(
            username = it.username,
            password = it.password
        )
    }


    override fun getUser(request: User): User? = repository.findByUsername(request.username)?.let {
        User(
            username = it.username,
            password = it.password
        )
    }

    override fun getByUserName(userName: String): User? = repository.findByUsername(userName)?.let {
        User(
            username = it.username,
            password = it.password
        )
    }
}
