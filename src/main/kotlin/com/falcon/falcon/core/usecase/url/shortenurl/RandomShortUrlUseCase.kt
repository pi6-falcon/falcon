package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import java.util.Base64
import java.util.UUID
import javax.validation.Valid
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Qualifier("randomShortUrlUseCase")
class RandomShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : ShortenUrl {

    private val log = KotlinLogging.logger {}

    override fun shorten(@Valid request: Url): Url {
        var shortUrl: String
        do {
            shortUrl = generateShortUrl(request.longUrl)
        } while (urlDataProvider.urlAlreadyExists(shortUrl))

        request.shortUrl = shortUrl
        return urlDataProvider.save(request)
    }

    fun generateShortUrl(longUrl: String): String =
        Base64.getUrlEncoder().encodeToString(
            UUID.randomUUID().toString().toByteArray()
        ).substring(0, 6)
}
