package com.falcon.falcon.core.usecase.trial

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

interface CreateTrialUser {

    fun execute(): User
}

@Service
class CreateTrialUserImpl(
    private val createUser: CreateUserUseCase,
    @Value("\${api.trial.default-expiration-in-hour}")
    private var trialDurationInHour: Long,
) : CreateTrialUser {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun execute(): User =
        createUser.execute(
            User(
                username = UUID.randomUUID().toString(),
                password = generateSecureRandomPassword(24),
                type = UserType.TRIAL,
                expirationDate = Instant.now().plus(trialDurationInHour, ChronoUnit.HOURS)
            )
        )

    private fun generateSecureRandomPassword(length: Long) = ThreadLocalRandom.current()
        .ints(length, 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("")
}
