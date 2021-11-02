package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.dataprovider.persistence.url.UrlDataProvider
import org.springframework.stereotype.Service

sealed interface GetUserUrlsUseCase {
    operator fun invoke(userIdentifier: String): List<Url>
}

@Service
class GetUserUrlsUseCaseImpl(private val dataProvider: UrlDataProvider) : GetUserUrlsUseCase {
    override fun invoke(userIdentifier: String) : List<Url> =
        dataProvider.getAllUrlsByUserIdentifier(userIdentifier)
}
