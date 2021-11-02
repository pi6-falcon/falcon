package com.falcon.falcon.core.usecase.urlredirect.history

import com.falcon.falcon.core.entity.UrlRedirectHistory
import com.falcon.falcon.dataprovider.feign.api.GeoIpFinder
import com.falcon.falcon.dataprovider.persistence.urlredirecthistory.UrlHistoryRedirectDataProvider
import org.springframework.stereotype.Service
import java.time.LocalDateTime

sealed interface UrlRedirectHistoryUseCase {

    suspend operator fun invoke(urlShort: String, from: String)

    fun getRedirectHistory(urlShort: String): List<UrlRedirectHistory>
}

@Service
class UrlRedirectHistoryUseCaseImpl(
    private val dataProvider: UrlHistoryRedirectDataProvider,
    private val geoIpFinder: GeoIpFinder
) : UrlRedirectHistoryUseCase {

    override suspend operator fun invoke(urlShort: String, from: String) {
        val location = geoIpFinder.getLocationByIp(from)
        dataProvider.saveAccess(
            UrlRedirectHistory(
                urlShort,
                from,
                location.city,
                location.country,
                LocalDateTime.now()
            )
        )
    }

    override fun getRedirectHistory(urlShort: String): List<UrlRedirectHistory> =
        dataProvider.getRedirects(urlShort)
}
