package com.falcon.falcon.entrypoint.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/api/ping")
    fun ping() = "pong"
}
