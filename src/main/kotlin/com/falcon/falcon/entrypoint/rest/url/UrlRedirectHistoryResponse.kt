package com.falcon.falcon.entrypoint.rest.url

import java.time.LocalDateTime

data class UrlRedirectHistoryResponse (
    val from: String,
    val city: String,
    val country: String,
    val date: LocalDateTime
)
