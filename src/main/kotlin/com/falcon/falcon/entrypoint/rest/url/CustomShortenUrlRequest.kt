package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class CustomShortenUrlRequest(
    @field:URL(message = "long_url should be a valid url")
    val longUrl: String,
    @field:NotNull(message = "custom_url cannot be null")
    val customUrl: String,
)

fun CustomShortenUrlRequest.toDomain() =
    Url(
        shortUrl = this.customUrl,
        longUrl = longUrl,
        type = UrlType.CUSTOM
    )
