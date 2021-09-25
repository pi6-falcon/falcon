package com.falcon.falcon.core.usecase.user


import com.falcon.falcon.dataprovider.persistence.user.UserDataProvider
import com.falcon.falcon.security.impl.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import kotlin.jvm.Throws

interface FindByUserName : UserDetailsService {

}

@Service
class FindByUserNameUseCases(private val userDataProvider: UserDataProvider) : FindByUserName {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String) = UserDetailsImpl(userDataProvider.getByUserName(username))

}