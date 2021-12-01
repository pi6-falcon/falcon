package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.InvalidUserCredentialsException
import com.falcon.falcon.core.exception.UserNotFoundException
import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface UpdateUserPasswordUseCase {

    fun execute(username: String, oldPassword: String, newPassword: String): User
}

@Service
class UpdateUserPasswordUseCaseImpl(
    private val userDataProvider: UserDataProvider,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : UpdateUserPasswordUseCase {

    override fun execute(username: String, oldPassword: String, newPassword: String): User {
        userDataProvider.findByUsername(username)?.let {
            validateOldPassword(oldPassword, it.password)

            it.password = bCryptPasswordEncoder.encode(newPassword)

            return userDataProvider.save(it)
        } ?: throw UserNotFoundException()
    }

    private fun validateOldPassword(rawPassword: String, encodedPassword: String) {
        if (!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
            throw InvalidUserCredentialsException();
        }
    }
}
