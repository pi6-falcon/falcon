package com.falcon.falcon.core.exception

class UrlNotFoundException(override val message: String = "a short_url with this identifier was not found") : RuntimeException() {
}
