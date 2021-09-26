package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.exception.ShortUrlAlreadyExistsException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import java.util.Base64
import java.util.UUID
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class RandomShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : UrlShortner {

    private val log = KotlinLogging.logger {}

    override fun shortenUrl(request: Url): Url {
        throw ShortUrlAlreadyExistsException()
        var shortUrl: String
        do {
            shortUrl = generateShortUrl(request.longUrl)
        } while (urlDataProvider.urlAlreadyExists(shortUrl))

        request.shortUrl = shortUrl
        return urlDataProvider.saveUrl(request)
    }

    fun generateShortUrl(longUrl: String): String =
        Base64.getUrlEncoder().encodeToString(
            UUID.randomUUID().toString().toByteArray()
        ).substring(0, 6)
}
