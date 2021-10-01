package com.falcon.falcon.core.usecase.exception.user

class UserNotFoundException(override val message: String? = "user not found") : RuntimeException()