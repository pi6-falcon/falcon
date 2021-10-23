package com.falcon.falcon.core.usecase.trial

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.security.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface CreateTrialToken {

    fun execute(request: User): String
}

@Service
class CreateTrialTokenImpl(
    private val jwtUtils: JwtUtils,
    @Value("\${api.trial.default-expiration-in-hour}")
    private var trialDurationInHour: Long,
) : CreateTrialToken {

    override fun execute(request: User): String =
        jwtUtils.generateToken(request.username, trialDurationInHour)
}
