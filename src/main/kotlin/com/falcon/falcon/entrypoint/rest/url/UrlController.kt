package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.usecase.url.CustomShortUrlUseCase
import com.falcon.falcon.core.usecase.url.RandomShortUrlUseCase
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/url")
class UrlController(
    private val generateRandomShortUrlUseCase: RandomShortUrlUseCase,
    private val generateCustomShortUrlUseCase: CustomShortUrlUseCase,
) {

    @PostMapping
    fun shortenToRandomUrl(@Valid @RequestBody request: RandomShortenUrlRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(generateRandomShortUrlUseCase.shortenUrl(request.toDomain()).toResponse(), HttpStatus.CREATED)

    @PostMapping("/custom")
    fun shortenToCustomUrl(@Valid @RequestBody request: CustomShortenUrlRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(generateCustomShortUrlUseCase.shortenUrl(request.toDomain()).toResponse(), HttpStatus.CREATED)
}

private fun Url.toResponse() =
    ShortenUrlResponse(
        shortUrl = this.shortUrl
    )
