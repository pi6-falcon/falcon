package com.falcon.falcon.entrypoint.rest.auth

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.auth.AuthUseCase
import com.falcon.falcon.security.utils.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authUseCase: AuthUseCase,
    private val jwtUtils: JwtUtils
) {

   @PostMapping
   fun authUser(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity(authUseCase.validateUser(request.toDomain()).toResponse(jwtUtils), HttpStatus.OK)

}

private fun AuthRequest.toDomain() = User(
    username = this.username,
    password = this.password
    )

private fun User.toResponse(jwtUtils: JwtUtils) =
    AuthResponse (token = jwtUtils.generateToken(this.username))
