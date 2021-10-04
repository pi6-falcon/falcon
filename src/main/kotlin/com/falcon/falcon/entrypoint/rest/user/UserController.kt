package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import com.falcon.falcon.core.usecase.user.FindUserUseCaseImpl
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: FindUserUseCaseImpl
) {

    @PostMapping
    fun createUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(createUserUseCase.execute(request.toDomain()).toResponse(), HttpStatus.CREATED)

    @GetMapping
    fun getUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(getUserUseCase.execute(request.toDomain()).toResponse(), HttpStatus.OK)
}

private fun User.toResponse() = UserResponse(
    username = this.username,
)
