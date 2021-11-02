package com.falcon.falcon.dataprovider.feign.api

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(url = "https://ipinfo.io", name = "GeoIP")
interface GeoIpFinder {

    @GetMapping(path = ["/{ip}/json"])
    fun getLocationByIp(@PathVariable ip: String): ApiResponse
}

data class ApiResponse(
    val city: String = "N/A",
    val country: String = "N/A",
)

