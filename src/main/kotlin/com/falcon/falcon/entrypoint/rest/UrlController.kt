package com.falcon.falcon.entrypoint.rest

import com.falcon.falcon.core.usecase.url.ShortenUrlUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/url")
class UrlController(private val shortenUrlUseCase: ShortenUrlUseCase) {

    @PostMapping
    fun shortenUrl(): String {
        shortenUrlUseCase.shortenUrl()
        return "ok"
    }
}
