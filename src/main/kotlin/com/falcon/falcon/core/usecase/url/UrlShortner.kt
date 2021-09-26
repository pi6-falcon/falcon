package com.falcon.falcon.core.usecase.url

import com.falcon.falcon.core.entity.Url

interface UrlShortner {

    fun shortenUrl(request: Url): Url
}
