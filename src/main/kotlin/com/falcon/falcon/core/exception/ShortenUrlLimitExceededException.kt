package com.falcon.falcon.core.exception

class ShortenUrlLimitExceededException(override val message: String = "user reached max number of shorten urls") : RuntimeException()
