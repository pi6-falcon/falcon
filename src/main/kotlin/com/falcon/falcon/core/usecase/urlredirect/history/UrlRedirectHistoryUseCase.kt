package com.falcon.falcon.core.usecase.urlredirect.history

import com.falcon.falcon.core.entity.UrlRedirectHistory
import com.falcon.falcon.dataprovider.persistence.urlredirecthistory.UrlHistoryRedirectDataProvider
import org.springframework.stereotype.Service
import java.time.LocalDateTime

sealed interface UrlRedirectHistoryUseCase {

    suspend operator fun invoke(urlShort: String, from: String)

    fun getAccess(urlShort: String): List<UrlRedirectHistory>
}

@Service
class UrlRedirectHistoryUseCaseImpl(private val dataProvider: UrlHistoryRedirectDataProvider) :
    UrlRedirectHistoryUseCase {

    override suspend operator fun invoke(urlShort: String, from: String) {
        dataProvider.saveAccess(UrlRedirectHistory(urlShort, from, LocalDateTime.now()))
    }

    override fun getAccess(urlShort: String): List<UrlRedirectHistory> =
        dataProvider.getListOfAccess(urlShort)
}
