package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import javax.validation.Valid
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Qualifier("customShortUrlUseCase")
class CustomShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : UrlShortener {

    private val log = KotlinLogging.logger {}

    override fun execute(@Valid request: Url): Url {
        if (urlDataProvider.urlAlreadyExists(request.shortUrl)) {
            log.error { "A identifier with the custom URL ${request.shortUrl} already exists." }
            throw ShortUrlAlreadyExistsException()
        }
        request.userIdentifier = SecurityContextHolder.getContext().authentication.name

        log.info { "Username   ${request.userIdentifier}" }

        return urlDataProvider.save(request)
    }
}
