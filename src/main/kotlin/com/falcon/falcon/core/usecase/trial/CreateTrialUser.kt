package com.falcon.falcon.core.usecase.trial

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

interface CreateTrialUser {

    fun execute(): User
}

@Service
class CreateTrialUserImpl(
    private val createUser: CreateUserUseCase,
    @Value("\${api.trial.default-expiration-in-hour}")
    private var trialDurationInHour: Long,
) : CreateTrialUser {
    override fun execute(): User =
        createUser.execute(
            User(
                username = UUID.randomUUID().toString(),
                password = UUID.randomUUID().toString(),
                type = UserType.TRIAL,
                expirationDate = Instant.now().plus(trialDurationInHour, ChronoUnit.HOURS)
            )
        )
}
