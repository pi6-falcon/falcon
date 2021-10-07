package com.falcon.falcon.security.utils

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
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
    var jwtUtils = JwtUtils("secret", "60000")

    @Test
    fun tokenNotValid() {
        Assertions.assertFalse(jwtUtils.isTokenValid(""))
    }

    @Test
    fun usernameNullInToken() {
        Assertions.assertEquals(null, jwtUtils.getUsernameFromToken(""))
    }

    @Test
    fun generateToke() {
         var result =  jwtUtils.generateToken("teste")

        result.shouldNotBeNull()
    }

    @Test
    fun tokenIsValid() {
        var token =  jwtUtils.generateToken("teste")

        var result = jwtUtils.isTokenValid(token)

        result.shouldBeTrue()
    }

    @Test
    fun tokenWithBlackSubject() {
        var token =  jwtUtils.generateToken("   ")

        var result = jwtUtils.isTokenValid(token)

        result.shouldBeFalse()
    }

}
