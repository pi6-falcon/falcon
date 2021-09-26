package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CustomShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : UrlShortner {

    private val log = KotlinLogging.logger {}

    override fun shortenUrl(request: Url): Url {
        if (urlDataProvider.urlAlreadyExists(request.shortUrl)) {
            throw ShortUrlAlreadyExistsException()
        }
        return urlDataProvider.saveUrl(request)
    }
}
