package com.falcon.falcon.core.entity

import com.falcon.falcon.core.enumeration.UserType
import java.time.Instant

data class User(
    val username: String,
    var password: String,
    val type: UserType = UserType.PERMANENT,
    val expirationDate: Instant? = null
)
