package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import javax.validation.Valid

interface ShortenUrl {

    fun shorten(@Valid request: Url): Url
}
