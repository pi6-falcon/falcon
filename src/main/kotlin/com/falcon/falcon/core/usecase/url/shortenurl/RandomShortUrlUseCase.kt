package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.exception.MaxNumberOfUrlsException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import java.util.Base64
import java.util.UUID
import javax.validation.Valid
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Qualifier("randomShortUrlUseCase")
class RandomShortUrlUseCase(
    private val urlDataProvider: UrlDataProvider,
    @Value("\${url.ttl}")
    private var ttl: String,
    @Value("\${url.maxUrlsTempUser}")
    private var maxUrlsTempUser: String) : UrlShortener {

    private val log = KotlinLogging.logger {}


    // TODO: Should populate user identifier and do some validations. Maybe limit the loop to specific number and return exception
    override fun execute(@Valid request: Url): Url {
        var shortUrl: String
        do {
            shortUrl = generateShortUrl()
        } while (urlDataProvider.urlAlreadyExists(shortUrl))

        request.shortUrl = shortUrl
        request.userIdentifier = SecurityContextHolder.getContext().authentication.name


        request.userIdentifier.isTempUser()?.let {
          urlDataProvider.getUrlsByUserIdentifier(request.userIdentifier)?.let { urls ->
              if (urls.size >= maxUrlsTempUser.toInt()) {
                  throw MaxNumberOfUrlsException()
              }
          }
            request.expiresIn = ttl.toLong()
        }

        return urlDataProvider.save(request)
    }

    fun generateShortUrl(): String =
        Base64.getUrlEncoder().encodeToString(
            UUID.randomUUID().toString().toByteArray()
        ).substring(0, 6)
}

fun String.isTempUser(): String? = this.takeIf {
    this.contains("ttl")
}
