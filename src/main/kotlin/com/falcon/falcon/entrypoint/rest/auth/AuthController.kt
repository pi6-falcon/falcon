package com.falcon.falcon.entrypoint.rest.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.security.JwtUtils
import com.falcon.falcon.core.security.UserDetailsImpl
import com.falcon.falcon.core.usecase.auth.AuthenticateUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authUseCase: AuthenticateUseCase,
    private val jwtUtils: JwtUtils,
) {

    @PostMapping
    fun authenticateUser(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity(authUseCase.execute(request.toDomain()).toResponse(jwtUtils), HttpStatus.OK)

    @PostMapping("/refresh")
    fun refreshToken(@RequestHeader(required = true, name = "Authorization") authorization: String): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(AuthResponse(jwtUtils.generateToken(
            jwtUtils.getUsernameFromToken(authorization.split(" ")[1])!!)
        ))
}

private fun Authentication.toUser() = (this.principal as UserDetailsImpl).getUser()

private fun AuthRequest.toDomain() = User(
    username = this.username,
    password = this.password
)

private fun User.toResponse(jwtUtils: JwtUtils) =
    AuthResponse(token = jwtUtils.generateToken(this.username))
