package com.falcon.falcon.entrypoint.rest.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.auth.AuthenticateUseCase
import com.falcon.falcon.security.utils.JwtUtils
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authUseCase: AuthenticateUseCase, private val jwtUtils: JwtUtils) {

    @PostMapping
    fun authenticateUser(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity(authUseCase.execute(request.toDomain()).toResponse(jwtUtils), HttpStatus.OK)
}

private fun AuthRequest.toDomain() = User(
    username = this.username,
    password = this.password
)

private fun User.toResponse(jwtUtils: JwtUtils) =
    AuthResponse(token = jwtUtils.generateToken(this.username))
