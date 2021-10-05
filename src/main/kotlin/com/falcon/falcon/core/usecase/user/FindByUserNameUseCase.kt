package com.falcon.falcon.core.usecase.user

import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import com.falcon.falcon.security.impl.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class FindByUserNameUseCase(private val userDataProvider: UserDataProvider) : UserDetailsService {

    override fun loadUserByUsername(username: String) = UserDetailsImpl(
        userDataProvider.findByUsername(username) ?: throw UsernameNotFoundException("username not found")
    )
}
