package com.falcon.falcon.entrypoint.rest.urlredirect

import com.falcon.falcon.core.usecase.urlredirect.UrlRedirectUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@RestController
class UrlRedirectController(
    private val redirectTo: UrlRedirectUseCase
) {
    @GetMapping("/{shortUrl}")
    fun redirect(@PathVariable shortUrl: String, request: HttpServletRequest): ModelAndView =
        ModelAndView(redirectTo(shortUrl, request))
}
