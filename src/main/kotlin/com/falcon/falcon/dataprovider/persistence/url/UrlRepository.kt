package com.falcon.falcon.dataprovider.persistence.url

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UrlRepository : CrudRepository<UrlEntity, String> {

    fun findByShortUrl(shortUrl: String): UrlEntity?

    fun findByShortUrlAndUserIdentifier(shortUrl: String, userIdentifier: String): UrlEntity?

    fun findAllByUserIdentifier(userIdentifier: String): List<UrlEntity>
}
