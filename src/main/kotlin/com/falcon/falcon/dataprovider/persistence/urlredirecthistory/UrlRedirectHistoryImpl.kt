package com.falcon.falcon.dataprovider.persistence.urlredirecthistory

import com.falcon.falcon.core.entity.UrlRedirectHistory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface UrlHistoryRedirectDataProvider {
    fun getRedirects(shortUrl: String): List<UrlRedirectHistory>

    fun saveAccess(urlRedirectHistory: UrlRedirectHistory)
}

@Service
class UrlRedirectHistoryImpl(private val repository: UrlRedirectHistoryRepository) : UrlHistoryRedirectDataProvider {

    override fun getRedirects(shortUrl: String): List<UrlRedirectHistory> =
        repository.findAllByShortUrl(shortUrl).map { it.toCoreEntity() }

    override fun saveAccess(urlRedirectHistory: UrlRedirectHistory) {
        repository.save(urlRedirectHistory.toDatabaseEntity())
    }
}

private fun UrlRedirectHistoryEntity.toCoreEntity(): UrlRedirectHistory = UrlRedirectHistory(
    shortUrl = this.shortUrl,
    from = this.from,
    date = LocalDateTime.parse(this.date)
)

private fun UrlRedirectHistory.toDatabaseEntity(): UrlRedirectHistoryEntity = UrlRedirectHistoryEntity(
    shortUrl = this.shortUrl,
    from = this.from,
    date = date.toString()
)
