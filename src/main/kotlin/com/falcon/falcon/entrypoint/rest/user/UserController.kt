package com.falcon.falcon.entrypoint.rest.user

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.CreateUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val createUserUseCase: CreateUserUseCase) {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<Void> {

        createUserUseCase.createUser(
            User(
             username = request.username,
             password = request.password)
        )

        return ResponseEntity(HttpStatus.OK)
    }
    
}