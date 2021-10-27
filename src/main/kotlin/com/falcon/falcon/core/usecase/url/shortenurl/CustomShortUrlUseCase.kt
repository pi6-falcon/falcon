package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.core.exception.ShortenUrlLimitExceededException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Service
@Validated
@Qualifier("customShortUrlUseCase")
class CustomShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : UrlShortener {

    private val log = KotlinLogging.logger {}

    override fun execute(@Valid request: Url, user: User): Url {
        if (urlDataProvider.urlAlreadyExists(request.shortUrl)) {
            log.error { "A identifier with the custom URL ${request.shortUrl} already exists." }
            throw ShortUrlAlreadyExistsException()
        }

        if (!isUserAllowedToShorten(user)) {
            throw ShortenUrlLimitExceededException()
        }

        return urlDataProvider.save(
            Url(
                shortUrl = request.shortUrl,
                longUrl = request.longUrl,
                userIdentifier = user.username,
                type = UrlType.CUSTOM,
                expirationDate = null
            )
        )
    }

    override fun getUrlCountByUser(user: User): Int = urlDataProvider.getAllUrlsByUserIdentifier(user.username).size
}
