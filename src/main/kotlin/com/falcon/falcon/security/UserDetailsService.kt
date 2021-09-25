package com.falcon.falcon.security

import com.falcon.falcon.core.entity.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.jvm.Throws

interface UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    fun findByUserName(username: String) : User

}