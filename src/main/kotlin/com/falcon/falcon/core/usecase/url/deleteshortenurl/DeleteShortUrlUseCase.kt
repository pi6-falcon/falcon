package com.falcon.falcon.core.usecase.url.deleteshortenurl

import com.falcon.falcon.core.exception.UrlNotFoundException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface DeleteShortenedUrl {

    fun execute(request: String)
}

@Service
class DeleteShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : DeleteShortenedUrl {

    private val log = KotlinLogging.logger {}

    // TODO: add validation if the user on the context is owner of the URL.
    override fun execute(request: String): Unit =
        urlDataProvider.getByShortUrl(request)?.let {
            return urlDataProvider.delete(it)
        } ?: throw UrlNotFoundException()
}
