package com.falcon.falcon.dataprovider.persistence.user

import com.falcon.falcon.core.entity.User
import org.springframework.stereotype.Service

interface UserDataProvider {

    fun save(request: User): User
    fun findByUsername(username: String): User?
    fun isUserAlreadyCreated(request: User): Boolean
}

@Service
class UserDataProviderImpl(private val repository: UserRepository) : UserDataProvider {

    override fun save(request: User): User = repository.save(request.toDatabaseEntity()).toCoreEntity()

    override fun findByUsername(username: String): User? =
        repository.findByUsername(username)?.toCoreEntity()

    override fun isUserAlreadyCreated(request: User): Boolean =
        repository.existsById(request.username)
}

private fun User.toDatabaseEntity(): UserEntity =
    UserEntity(
        username = this.username,
        password = this.password
    )

private fun UserEntity.toCoreEntity(): User =
    User(
        username = this.username,
        password = this.password
    )
