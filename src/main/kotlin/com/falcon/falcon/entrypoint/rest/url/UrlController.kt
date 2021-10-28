package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.security.UserDetailsImpl
import com.falcon.falcon.core.usecase.url.deleteshortenurl.DeleteShortenedUrl
import com.falcon.falcon.core.usecase.url.shortenurl.UrlShortener
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/url")
class UrlController(
    @Qualifier("randomShortUrlUseCase")
    private val randomShortUrlUseCase: UrlShortener,
    @Qualifier("customShortUrlUseCase")
    private val customShortUrlUseCase: UrlShortener,
    private val deleteShortenedUrlUseCase: DeleteShortenedUrl,
) {

    @PostMapping
    fun shortenToRandomUrl(@RequestBody @Valid request: RandomShortenUrlRequest, authentication: Authentication): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(randomShortUrlUseCase.execute(request.toDomain(), authentication.toUser()).toResponse(), HttpStatus.CREATED)

    @PostMapping("/custom")
    fun shortenToCustomUrl(@Valid @RequestBody request: CustomShortenUrlRequest, authentication: Authentication): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(customShortUrlUseCase.execute(request.toDomain(), authentication.toUser()).toResponse(), HttpStatus.CREATED)

    @DeleteMapping("/{shortenUrl}")
    fun deleteShortenedUrl(@Valid @PathVariable("shortenUrl") request: String, authentication: Authentication): ResponseEntity<Void> =
        deleteShortenedUrlUseCase.execute(request, authentication.toUser()).run {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
}

private fun Authentication.toUser() = (this.principal as UserDetailsImpl).getUser()

private fun Url.toResponse() =
    ShortenUrlResponse(
        shortUrl = this.shortUrl
    )
