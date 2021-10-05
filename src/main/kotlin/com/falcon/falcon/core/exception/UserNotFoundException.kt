package com.falcon.falcon.core.exception

class UserNotFoundException(override val message: String? = "user with this username was not found") : RuntimeException()
