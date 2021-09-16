package com.falcon.falcon.dataprovider.persistence

import com.falcon.falcon.dataprovider.persistence.url.UrlRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface UrlDataProvider {

    /*
    this class is responsible for converting core entity to persistence entity and vice-versa.
     */
    fun saveUrl()
    fun updateUrl()
}

@Service
class UrlDataProviderImpl(private val repository: UrlRepository) : UrlDataProvider {

    private val log = KotlinLogging.logger {}

    override fun saveUrl() {
        log.info { "saving url..." }
    }

    override fun updateUrl() {
        log.info { "updating url..." }
    }

}
