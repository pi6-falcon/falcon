package com.falcon.falcon.entrypoint.rest.url

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.entity.UrlRedirectHistory
import com.falcon.falcon.core.security.UserDetailsImpl
import com.falcon.falcon.core.usecase.url.deleteshortenurl.DeleteShortenedUrl
import com.falcon.falcon.core.usecase.url.shortenurl.UrlShortener
import com.falcon.falcon.core.usecase.urlredirect.history.UrlRedirectHistoryUseCase
import com.sun.net.httpserver.HttpServer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/url")
class UrlController(
    @Qualifier("randomShortUrlUseCase")
    private val randomShortUrlUseCase: UrlShortener,
    @Qualifier("customShortUrlUseCase")
    private val customShortUrlUseCase: UrlShortener,
    private val deleteShortenedUrlUseCase: DeleteShortenedUrl,
    private val urlRedirectHistoryUseCase: UrlRedirectHistoryUseCase
) {

    @PostMapping
    fun shortenToRandomUrl(@RequestBody @Valid request: RandomShortenUrlRequest, authentication: Authentication, from: HttpServletRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(randomShortUrlUseCase.execute(request.toDomain(), authentication.toUser()).toResponse(from), HttpStatus.CREATED)

    @PostMapping("/custom")
    fun shortenToCustomUrl(@Valid @RequestBody request: CustomShortenUrlRequest, authentication: Authentication, from: HttpServletRequest): ResponseEntity<ShortenUrlResponse> =
        ResponseEntity(customShortUrlUseCase.execute(request.toDomain(), authentication.toUser()).toResponse(from), HttpStatus.CREATED)

    @DeleteMapping("/{shortenUrl}")
    fun deleteShortenedUrl(@Valid @PathVariable("shortenUrl") request: String, authentication: Authentication): ResponseEntity<Void> =
        deleteShortenedUrlUseCase.execute(request, authentication.toUser()).run {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }

    @GetMapping("/{shortenUrl}/metric")
    fun getRedirectHistory(@PathVariable("shortenUrl") shortUrl: String ): ResponseEntity<List<UrlRedirectHistoryResponse>> =
        ResponseEntity.ok(urlRedirectHistoryUseCase.getRedirectHistory(shortUrl).map { it.toResponse() })
}

private fun Authentication.toUser() = (this.principal as UserDetailsImpl).getUser()

private fun Url.toResponse(from: HttpServletRequest) =
    ShortenUrlResponse(
        shortUrl = "${from.requestURL.toString().replace(from.requestURI, "")}/${this.shortUrl}"
    )


private fun UrlRedirectHistory.toResponse(): UrlRedirectHistoryResponse = UrlRedirectHistoryResponse(
    from = this.from,
    date = this.date
)
