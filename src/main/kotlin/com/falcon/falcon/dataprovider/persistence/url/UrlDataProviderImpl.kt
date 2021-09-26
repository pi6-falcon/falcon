package com.falcon.falcon.dataprovider.persistence.url

import com.falcon.falcon.core.entity.Url
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface UrlDataProvider {

    fun saveUrl(request: Url): Url
    fun urlAlreadyExists(shortUrl: String): Boolean
}

@Service
class UrlDataProviderImpl(private val repository: UrlRepository) : UrlDataProvider {

    private val log = KotlinLogging.logger {}

    override fun saveUrl(request: Url) =
        repository.save(request.toEntity()).toUrl()

    override fun urlAlreadyExists(shortUrl: String): Boolean =
        repository.existsById(shortUrl)

}

private fun Url.toEntity(): UrlEntity =
    UrlEntity(
        shortUrl = this.shortUrl ?: "",
        longUrl = this.longUrl ?: "",
        userIdentifier = this.userIdentifier ?: ""
    )

private fun UrlEntity.toUrl(): Url =
    Url(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        userIdentifier = this.userIdentifier
    )
