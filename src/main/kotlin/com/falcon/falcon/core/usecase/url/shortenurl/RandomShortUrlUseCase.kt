package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.enumeration.UserType
import com.falcon.falcon.core.exception.ShortenUrlLimitExceededException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64
import java.util.UUID
import javax.validation.Valid

@Service
@Validated
@Qualifier("randomShortUrlUseCase")
class RandomShortUrlUseCase(
    private val urlDataProvider: UrlDataProvider,
    @Value("\${api.trial.default-expiration-in-hour}")
    private var trialDurationInHour: Long,
) : UrlShortener {

    private val iteratorLimit = 5
    private val log = KotlinLogging.logger {}

    override fun execute(@Valid request: Url, user: User): Url {
        if (!isUserAllowedToShorten(user)) {
            throw ShortenUrlLimitExceededException()
        }
        val shortUrl = generateUniqueShortUrl()

        return urlDataProvider.save(
            Url(
                shortUrl = shortUrl,
                longUrl = request.longUrl,
                userIdentifier = user.username,
                type = UrlType.RANDOM,
                expirationDate = if (user.type == UserType.TRIAL) Instant.now().plus(trialDurationInHour, ChronoUnit.HOURS) else null
            )
        )
    }

    override fun getUrlCountByUser(user: User): Int = urlDataProvider.getAllUrlsByUserIdentifier(user.username).size

    fun generateUniqueShortUrl(): String {
        var hash: String
        var currentIterator = 0
        do {
            hash = generateHash()
            currentIterator++
            if (currentIterator >= iteratorLimit) throw RuntimeException("max iterator shortening url exceeded.")
        } while (urlDataProvider.urlAlreadyExists(hash))
        return hash
    }

    private fun generateHash(): String =
        Base64.getUrlEncoder().encodeToString(
            UUID.randomUUID().toString().toByteArray()
        ).substring(0, 6)
}
