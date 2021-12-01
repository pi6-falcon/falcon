package com.falcon.falcon.entrypoint.rest.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class UserPasswordRequest(
    @field:NotBlank(message = "old_password must be sent")
    @field:Length(min = 3, max = 64, message = "old_password must be between 3 and 64 characters")
    val oldPassword: String?,
    @field:NotBlank(message = "new_password must be sent")
    @field:Length(min = 3, max = 64, message = "new_password must be between 3 and 64 characters")
    val newPassword: String?
)

