package com.falcon.falcon.core.entity

import java.time.LocalDateTime

data class UrlRedirectHistory(
    val shortUrl: String,
    val from: String, // the ip address
    val date: LocalDateTime, //
)
