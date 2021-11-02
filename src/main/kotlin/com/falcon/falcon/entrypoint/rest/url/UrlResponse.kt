package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.enumeration.UrlType
import java.time.Instant

data class UrlResponse(
    val shortUrl: String,
    val longUrl: String,
    val type: UrlType,
    val expirationDate: Instant?
)
