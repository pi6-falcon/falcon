package com.falcon.falcon.utils

import com.google.common.io.BaseEncoding
import org.apache.tomcat.util.codec.binary.BaseNCodec
import java.util.*

object UrlUtils {

    private const val DELIMITER = '-';

    fun generate() = BaseEncoding.base64Url().encode(
            UUID.randomUUID().toString().split(DELIMITER)[0].encodeToByteArray()).toString()

}