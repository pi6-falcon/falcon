//package com.falcon.falcon.dataprovider.persistence.user
//
//import com.falcon.falcon.core.entity.User
//import io.mockk.every
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.junit5.MockKExtension
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//
//@ExtendWith(MockKExtension::class)
//class UserDataProviderImplTest {
//
//    @RelaxedMockK
//    lateinit var userRepository: UserRepository
//
//    private lateinit var userDataProvider: UserDataProvider
//
//    private val user: User = User(
//        username = "test",
//        password = "123"
//    )
//
//    private val userEntity: UserEntity = UserEntity(
//        username = user.username,
//        password = user.password
//    )
//
//    @Test
//    @DisplayName("UserDataProvider return user not exist test")
//    fun findUserNameIsNullTest() {
//
//        userDataProvider = UserDataProviderImpl(userRepository)
//
//        every { userRepository.findByUsername(user.username) } returns null
//
//        Assertions.assertEquals(null, userDataProvider.findByUsername(user.username))
//    }
//
//    @Test
//    @DisplayName("UserDataProvider return user exist test")
//    fun findUserIsNotNullTest() {
//
//        userDataProvider = UserDataProviderImpl(userRepository)
//
//        every { userRepository.findByUsername(user.username) } returns userEntity
//
//        Assertions.assertEquals(user
//            ,userDataProvider.findByUsername(user.username))
//    }
//
//    @Test
//    @DisplayName("UserDataProvider return user not exist test")
//    fun getUserIsNullTest() {
//
//        userDataProvider = UserDataProviderImpl(userRepository)
//
//        every { userRepository.findByUsername(user.username) } returns null
//
//        Assertions.assertEquals(null, userDataProvider.find(user))
//    }
//
//    @Test
//    @DisplayName("UserDataProvider return user exist test")
//    fun getUserIsNotNullTest() {
//
//        userDataProvider = UserDataProviderImpl(userRepository)
//
//        every { userRepository.findByUsername(user.username) } returns userEntity
//
//        Assertions.assertEquals(user, userDataProvider.find(user))
//    }
//
//}