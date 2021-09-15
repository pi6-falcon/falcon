package com.falcon.falcon.utils

import com.google.common.io.BaseEncoding
import java.util.*

object UrlUtils {

    fun generate() = BaseEncoding.base64Url().encode(
        UUID.randomUUID().toString().toByteArray()).substring(0, 6)

}
