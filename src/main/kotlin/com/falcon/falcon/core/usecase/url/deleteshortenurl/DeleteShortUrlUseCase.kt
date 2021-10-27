package com.falcon.falcon.core.usecase.url.deleteshortenurl

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.exception.UrlNotFoundException
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service

interface DeleteShortenedUrl {

    fun execute(request: String, user: User)
}

@Service
class DeleteShortUrlUseCase(private val urlDataProvider: UrlDataProvider) : DeleteShortenedUrl {

    private val log = KotlinLogging.logger {}

    override fun execute(request: String, user: User): Unit =
        urlDataProvider.getByShortUrlAndUserIdentifier(request, user.username)?.let {
            return urlDataProvider.delete(it)
        } ?: throw UrlNotFoundException()
}
