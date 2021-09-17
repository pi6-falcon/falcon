package com.falcon.falcon.entrypoint.rest.user

import java.sql.Time
import java.time.Instant

data class UserResponse(
    val username: String,
    val password: String,
    val createdAt: Instant = Instant.now()
)
