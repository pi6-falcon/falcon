package com.falcon.falcon.utils

import java.util.*

object UrlUtils {

    private const val DELIMITER = '-';

    fun generate() = Base64.getEncoder()
        .encodeToString(
            UUID.randomUUID().toString().split(DELIMITER)[0].encodeToByteArray()).toString()

}