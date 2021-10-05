package com.falcon.falcon.entrypoint.rest.auth

import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AuthRequest(
    @field:NotBlank(message = "username must be sent")
    val username: String,
    @field:Length(min = 3, max = 64, message = "password must be between 3 and 64 characters")
    val password: String
)
