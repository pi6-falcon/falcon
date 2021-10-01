package com.falcon.falcon.core.usecase.exception.auth

class UserDataNotValidException (override val message: String? = "username or password not match") : RuntimeException()