package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import javax.validation.Valid

interface UrlShortener {

    fun execute(@Valid request: Url): Url
}
