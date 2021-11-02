package com.falcon.falcon

import com.falcon.falcon.entrypoint.rest.auth.AuthRequest
import com.falcon.falcon.entrypoint.rest.url.CustomShortenUrlRequest
import com.falcon.falcon.entrypoint.rest.url.RandomShortenUrlRequest
import com.falcon.falcon.entrypoint.rest.url.ShortenUrlResponse
import com.falcon.falcon.entrypoint.rest.user.UserRequest
import com.fasterxml.jackson.databind.ObjectMapper

class CreationUtils {
    companion object {

        fun buildRandomShortenUrlRequest(longUrl: String? = "this-is-a-dummy-long-url"): RandomShortenUrlRequest =
            RandomShortenUrlRequest(longUrl = longUrl)

        fun buildCustomShortenUrlRequest(longUrl: String? = "this-is-a-dummy-long-url", customUrl: String? = "this-is-a-dummy-custom-url"): CustomShortenUrlRequest =
            CustomShortenUrlRequest(longUrl = longUrl, customUrl = customUrl)

        fun buildShortenUrlResponse(shortUrl: String = "this-is-a-dummy-short-url"): ShortenUrlResponse =
            ShortenUrlResponse(shortUrl = shortUrl)

        fun buildAuthRequest(username: String? = "teste", password: String? = "123"): AuthRequest =
            AuthRequest(username!!, password!!)

        fun buildUserRequest(username: String? = "teste", password: String? = "123"): UserRequest =
            UserRequest(username!!, password!!)

    }
}


fun Any.asString() = ObjectMapper().writeValueAsString(this)

