package com.falcon.falcon.entrypoint.rest.auth

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class AuthRequest(
    @NotBlank
    val username: String,
    @Length(min = 3, max = 64)
    val password: String
)
