package com.falcon.falcon.dataprovider.persistence.url

import com.falcon.falcon.core.entity.Url
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.streams.toList

interface UrlDataProvider {

    fun save(request: Url): Url
    fun urlAlreadyExists(shortUrl: String): Boolean
    fun delete(request: Url)
    fun getByShortUrl(shortUrl: String): Url?
    fun getUrlsByUserIdentifier(userIdentifier: String): List<Url>?
}

@Service
class UrlDataProviderImpl(private val repository: UrlRepository) : UrlDataProvider {

    private val log = KotlinLogging.logger {}

    override fun save(request: Url) =
        repository.save(request.toDatabaseEntity()).toCoreEntity()

    override fun urlAlreadyExists(shortUrl: String): Boolean =
        repository.existsById(shortUrl)

    override fun delete(request: Url) =
        repository.delete(request.toDatabaseEntity())

    override fun getByShortUrl(shortUrl: String): Url? =
        repository.findByShortUrl(shortUrl)?.toCoreEntity()

    override fun getUrlsByUserIdentifier(userIdentifier: String): List<Url>? =
        repository.findByUserIdentifier(userIdentifier)?.stream()?.map {
            it -> it.toCoreEntity()
        }?.toList()
}

private fun Url.toDatabaseEntity(): UrlEntity =
    UrlEntity(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        userIdentifier = this.userIdentifier,
        timeToLive = this.expiresIn
    )

private fun UrlEntity.toCoreEntity(): Url =
    Url(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        userIdentifier = this.userIdentifier,
        expiresIn = this.timeToLive
    )
