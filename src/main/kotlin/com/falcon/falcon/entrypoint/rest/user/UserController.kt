package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import com.falcon.falcon.core.usecase.user.GetUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase) {

    @PostMapping
    fun createUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(createUserUseCase.createUser(
                User(username = request.username, password = request.password)).toResponse(), HttpStatus.OK)

    @GetMapping
    fun getUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(getUserUseCase.getUser(
            User(username = request.username, password = request.password)).toResponse(), HttpStatus.OK)
}

private fun User.toResponse() = UserResponse(
    username = this.username,
    password = this.password
)