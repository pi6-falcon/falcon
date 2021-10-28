package com.falcon.falcon.core.usecase.urlredirect

import com.falcon.falcon.core.exception.UrlNotFoundException
import com.falcon.falcon.core.usecase.urlredirect.history.UrlRedirectHistoryUseCase
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

sealed interface UrlRedirectUseCase {
    operator fun invoke(request: String, from: HttpServletRequest): String
}

@Service
@OptIn(DelicateCoroutinesApi::class)
class UrlRedirectUseCaseImpl(
    private val urlDataProvider: UrlDataProvider,
    private val saveHistory: UrlRedirectHistoryUseCase
) : UrlRedirectUseCase {

    private val log = KotlinLogging.logger {}

    override fun invoke(request: String, from: HttpServletRequest): String =
        urlDataProvider.getByShortUrl(request)?.let {
            log.info { "starting request with $request and url ${from.requestURL}" }
            GlobalScope.launch {
                log.info { "enter async" }
                saveHistory(it.shortUrl, from.remoteAddr)
                log.info { "finish inside async" }
            }
            log.info { "finish outside async" }

            "redirect:${it.longUrl}"
        } ?: throw UrlNotFoundException()
}





