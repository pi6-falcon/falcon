package com.falcon.falcon.core.exception

class ShortUrlAlreadyExistsException(override val message: String = "a short_url with this text already exists") : RuntimeException() {
}
