package com.falcon.falcon.dataprovider.persistence.url

import mu.KotlinLogging
import org.springframework.stereotype.Service

interface UrlDataProvider {

    fun saveUrl()
    fun updateUrl()
}

@Service
class UrlDataProviderImpl(private val repository: UrlRepository) : UrlDataProvider {

    private val log = KotlinLogging.logger {}

    override fun saveUrl() {
        repository.save(Url("shortUrl", "longUrl", "userIdentifier"))
    }

    override fun updateUrl() {
        repository.save(Url("shortUrl", "longUrl", "userIdentifier"))
    }

}
