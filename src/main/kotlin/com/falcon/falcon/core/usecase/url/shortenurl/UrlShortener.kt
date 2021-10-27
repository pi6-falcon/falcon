package com.falcon.falcon.core.usecase.url.shortenurl

import com.falcon.falcon.core.entity.Url
import com.falcon.falcon.core.entity.User
import javax.validation.Valid

interface UrlShortener {

    fun execute(@Valid request: Url, user: User): Url

    fun isUserAllowedToShorten(user: User): Boolean = (getUrlCountByUser(user) < user.type.urlLimit)

    fun getUrlCountByUser(user: User): Int
}
