package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.enumeration.UrlType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CustomShortenUrlRequest(
    @field:NotBlank(message = "long_url must be sent")
    val longUrl: String?,
    @field:NotBlank(message = "custom_url must be sent")
    @field:Size(min = 3, max = 20, message = "custom_url must be between 3 and 20 characters")
    val customUrl: String?,
)

fun CustomShortenUrlRequest.toDomain() =
    Url(
        shortUrl = this.customUrl!!,
        longUrl = longUrl!!,
        type = UrlType.CUSTOM
    )
