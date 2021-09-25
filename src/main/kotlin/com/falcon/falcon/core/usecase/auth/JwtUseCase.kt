package com.falcon.falcon.core.usecase.auth

import com.falcon.falcon.core.entity.User
import org.springframework.stereotype.Service

interface JwtCreate {

    fun createJwt(user: User): String

}

@Service
class JwtUseCase(): JwtCreate {

    override fun createJwt(user: User): String {
        return "";
    }

}