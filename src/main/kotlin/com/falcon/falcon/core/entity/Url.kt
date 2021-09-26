package com.falcon.falcon.core.entity

import com.falcon.falcon.core.enumeration.UrlType

data class Url(
    var shortUrl: String = "",
    val longUrl: String = "",
    val userIdentifier: String = "",
    val type: UrlType  = UrlType.RANDOM
)
