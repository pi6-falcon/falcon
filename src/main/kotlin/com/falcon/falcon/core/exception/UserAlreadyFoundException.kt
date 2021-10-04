package com.falcon.falcon.core.exception

class UserAlreadyFoundException(override val message: String? = "user with this username already exists") : RuntimeException()
