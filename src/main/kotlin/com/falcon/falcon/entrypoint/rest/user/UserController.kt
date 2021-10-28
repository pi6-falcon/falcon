package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
) {

    @PostMapping
    fun createUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(createUserUseCase.execute(request.toDomain()).toResponse(), HttpStatus.CREATED)
}

private fun User.toResponse() = UserResponse(
    username = this.username,
)
