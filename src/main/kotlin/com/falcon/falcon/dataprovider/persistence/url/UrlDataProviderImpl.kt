package com.falcon.falcon.dataprovider.persistence.url

import com.falcon.falcon.core.entity.Url
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface UrlDataProvider {

    fun save(request: Url): Url
    fun urlAlreadyExists(shortUrl: String): Boolean
    fun delete(request: Url)
    fun getByShortUrl(shortUrl: String): Url?
}

@Service
class UrlDataProviderImpl(private val repository: UrlRepository) : UrlDataProvider {

    private val log = KotlinLogging.logger {}

    override fun save(request: Url) =
        repository.save(request.toEntity()).toUrl()

    override fun urlAlreadyExists(shortUrl: String): Boolean =
        repository.existsById(shortUrl)

    override fun delete(request: Url) =
        repository.delete(request.toEntity())

    override fun getByShortUrl(shortUrl: String): Url? =
        repository.findByShortUrl(shortUrl)?.toUrl()
}

private fun Url.toEntity(): UrlEntity =
    UrlEntity(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        userIdentifier = this.userIdentifier
    )

private fun UrlEntity.toUrl(): Url =
    Url(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        userIdentifier = this.userIdentifier
    )
