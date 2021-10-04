package com.falcon.falcon.security.utils

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JwtUtilsTest {

    @InjectMockKs
    var jwtUtils = JwtUtils()

    @Test
    @DisplayName("token not valid")
    fun tokenNotValid() {
        Assertions.assertFalse(jwtUtils.isTokenValid(""))
    }

    @Test
    @DisplayName("get username in jwt")
    fun usernameNullInToken() {
        Assertions.assertEquals(null, jwtUtils.getUsernameFromToken(""))
    }

    @Test
    @DisplayName("generate jwt without envs")
    fun generateTokenWithoutEnvs() {
        assertThrows<UninitializedPropertyAccessException> { jwtUtils.generateToken("teste") }
    }
}
