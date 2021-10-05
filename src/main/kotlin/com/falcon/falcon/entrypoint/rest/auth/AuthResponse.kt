package com.falcon.falcon.entrypoint.rest.auth

import java.time.Instant

data class AuthResponse(
    val token: String,
    val createdAt: Instant = Instant.now()
)
