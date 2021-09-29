package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.usecase.url.deleteshortenurl.DeleteShortenedUrl
import com.falcon.falcon.core.usecase.url.shortenurl.ShortenUrl
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/url")
class UrlController(
    @Qualifier("randomShortUrlUseCase")
    private val randomShortUrlUseCase: ShortenUrl,
    @Qualifier("customShortUrlUseCase")
    private val customShortUrlUseCase: ShortenUrl,
    private val deleteShortenedUrlUseCase: DeleteShortenedUrl,
) {

    @PostMapping
    fun shortenToRandomUrl(@Valid @RequestBody request: RandomShortenUrlRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(randomShortUrlUseCase.shorten(request.toDomain()).toResponse(), HttpStatus.CREATED)

    @PostMapping("/custom")
    fun shortenToCustomUrl(@Valid @RequestBody request: CustomShortenUrlRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(customShortUrlUseCase.shorten(request.toDomain()).toResponse(), HttpStatus.CREATED)

    @DeleteMapping("/{shortenUrl}")
    fun deleteShortenedUrl(@Valid @PathVariable("shortenUrl") request: String): ResponseEntity<Void> =
        deleteShortenedUrlUseCase.delete(request).run {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
}

private fun Url.toResponse() =
    ShortenUrlResponse(
        shortUrl = this.shortUrl
    )
