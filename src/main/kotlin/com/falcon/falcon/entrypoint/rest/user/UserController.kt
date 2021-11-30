package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import com.falcon.falcon.core.usecase.user.DeleteUserUseCase
import com.falcon.falcon.core.usecase.user.UpdateUserPasswordUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {

    @PostMapping
    fun createUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity(createUserUseCase.execute(request.toDomain()).toResponse(), HttpStatus.CREATED)

    @PutMapping("/{username}")
    fun updatePasswordUser(
        @PathVariable username: String,
        @RequestBody @Valid request: UserPasswordRequest
    ): ResponseEntity<UserResponse> =
        ResponseEntity(
            updateUserPasswordUseCase.execute(username, request.oldPassword!!, request.newPassword!!).toResponse(),
            HttpStatus.OK
        )

    @DeleteMapping("/{username}")
    fun deleteUser(
        @Valid @PathVariable("username") request: String,
        authentication: Authentication
    ): ResponseEntity<Void> =
        deleteUserUseCase.execute(request).run {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
}

private fun User.toResponse() = UserResponse(
    username = this.username,
)
