package com.falcon.falcon.utils

import com.google.common.io.BaseEncoding
import org.apache.tomcat.util.codec.binary.BaseNCodec
import java.util.*

object UrlUtils {

    private const val DELIMITER = '-';

    private const val ZERO = 0;

    private const val SIX = 6;

    fun generate() = BaseEncoding.base64Url().encode(
            UUID.randomUUID().toString().split(DELIMITER)[ZERO].encodeToByteArray()).substring(ZERO, SIX)

}