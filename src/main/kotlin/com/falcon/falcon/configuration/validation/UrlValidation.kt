package com.falcon.falcon.configuration.validation

import org.apache.commons.validator.routines.UrlValidator
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class UrlValidation {

    @Bean
    fun urlValidator(): UrlValidator = UrlValidator()
}
