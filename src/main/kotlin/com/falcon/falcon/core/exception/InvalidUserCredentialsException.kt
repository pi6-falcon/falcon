package com.falcon.falcon.core.exception

class InvalidUserCredentialsException(override val message: String? = "username or password does not match") : RuntimeException()
