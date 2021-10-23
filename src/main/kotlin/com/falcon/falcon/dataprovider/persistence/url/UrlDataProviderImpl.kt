package com.falcon.falcon.dataprovider.persistence.url

import com.falcon.falcon.core.entity.Url
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant

interface UrlDataProvider {

    fun save(request: Url): Url
    fun urlAlreadyExists(shortUrl: String): Boolean
    fun delete(request: Url)
    fun getByShortUrl(shortUrl: String): Url?
    fun getByShortUrlAndUserIdentifier(shortUrl: String, userIdentifier: String): Url?
    fun getAllUrlsByUserIdentifier(userIdentifier: String): List<Url>
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

    override fun getByShortUrlAndUserIdentifier(shortUrl: String, userIdentifier: String): Url? =
        repository.findByShortUrlAndUserIdentifier(shortUrl, userIdentifier)?.toCoreEntity()


    override fun getAllUrlsByUserIdentifier(userIdentifier: String): List<Url> {
        return repository.findAllByUserIdentifier(userIdentifier).map { it.toCoreEntity() }
    }
}

private fun Url.toDatabaseEntity(): UrlEntity =
    UrlEntity(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        type = this.type,
        userIdentifier = this.userIdentifier,
        expirationDate = this.expirationDate?.epochSecond
    )

private fun UrlEntity.toCoreEntity(): Url =
    Url(
        shortUrl = this.shortUrl,
        longUrl = this.longUrl,
        type = this.type,
        userIdentifier = this.userIdentifier,
        expirationDate = this.expirationDate?.let { Instant.ofEpochSecond(it) }
    )
