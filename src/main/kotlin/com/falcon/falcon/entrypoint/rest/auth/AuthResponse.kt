package com.falcon.falcon.entrypoint.rest.auth

import java.sql.Time
import java.time.Instant

data class AuthResponse(
    val token       : String,
    val createdAt   : Instant = Instant.now()
)
