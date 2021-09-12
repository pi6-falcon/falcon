package com.falcon.falcon

import com.falcon.falcon.utils.UrlUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FalconApplication

fun main(args: Array<String>) {
	runApplication<FalconApplication>(*args)
}
