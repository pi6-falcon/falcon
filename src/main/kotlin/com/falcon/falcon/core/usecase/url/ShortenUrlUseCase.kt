package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface ShortenUrl {

    fun shortenUrl()
}

@Service
class ShortenUrlUseCase(private val urlDataProvider: UrlDataProvider) : ShortenUrl {

    private val log = KotlinLogging.logger {}

    override fun shortenUrl() {
        urlDataProvider.saveUrl()
    }

}
