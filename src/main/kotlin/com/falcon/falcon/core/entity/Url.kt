package com.falcon.falcon.core.entity

import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.entrypoint.common.validation.url.ValidUrl
import java.time.Instant

data class Url(
    val shortUrl: String = "",
    @ValidUrl
    val longUrl: String = "",
    val userIdentifier: String = "",
    val type: UrlType = UrlType.RANDOM,
    val expirationDate: Instant? = Instant.now()
)
