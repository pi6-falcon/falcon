package com.falcon.falcon.core.entity

import java.time.LocalDateTime

data class UrlRedirectHistory(
    val shortUrl: String,
    val from: String,
    val city: String,
    val country: String,
    val date: LocalDateTime
)
