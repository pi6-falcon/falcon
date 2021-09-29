package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import javax.validation.constraints.NotBlank

data class RandomShortenUrlRequest(
    @field:NotBlank(message = "long_url should be sent")
    val longUrl: String?
)

fun RandomShortenUrlRequest.toDomain() =
    Url(
        longUrl = this.longUrl!!,
        type = UrlType.RANDOM
    )
