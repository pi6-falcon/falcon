package com.falcon.falcon.core.entity

import com.falcon.falcon.core.enumeration.UrlType
import com.falcon.falcon.entrypoint.common.validation.url.ValidUrl

data class Url(
    var shortUrl: String = "",
    @ValidUrl
    val longUrl: String = "",
    val userIdentifier: String = "", // TODO: pending until JWT is implemented
    val type: UrlType = UrlType.RANDOM
)
