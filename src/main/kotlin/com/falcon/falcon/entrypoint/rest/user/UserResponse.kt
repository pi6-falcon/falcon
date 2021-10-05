package com.falcon.falcon.entrypoint.rest.user

import java.time.Instant

data class UserResponse(
    val username: String,
    val createdAt: Instant = Instant.now()
)
