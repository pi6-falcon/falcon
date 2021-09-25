package com.falcon.falcon.constants

import org.springframework.http.HttpMethod

enum class AppRoutes(val path: String, val httpMethod: Array<HttpMethod>) {
    USER("/user", arrayOf(HttpMethod.POST, HttpMethod.GET)),
    AUTH("/auth", arrayOf(HttpMethod.POST))
}