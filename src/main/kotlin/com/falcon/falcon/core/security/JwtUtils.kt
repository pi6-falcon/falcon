package com.falcon.falcon.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import java.util.concurrent.TimeUnit
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtils(
    @Value("\${api.jwt.secret}")
    private var secret: String,
    @Value("\${api.jwt.default-expiration-in-hour}")
    private var defaultDuration: Long
) {

    /**
     * Function responsible for generating the JWT token
     *
     * @param username is the username
     * @param duration is the duration in hour. If it is not passed,
     * the default value informed in the properties.yml will be used instead
     *
     * @return is the token already split, or null if none/invalid
     */
    fun generateToken(username: String, duration: Long? = null): String =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + fetchDuration(duration)))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()

    /**
     * Function responsible for validating JWT token
     *
     * @param token is the JWT token itself
     *
     * @return true if token is present, has the subject and expiration date is valid, otherwise it will return false
     */
    fun isTokenValid(token: String): Boolean =
        getClaimsToken(token)?.let {
            val expirationDate = it.expiration
            it.subject != null && expirationDate.isValid()
        } ?: false

    fun getUsernameFromToken(token: String): String? =
        getClaimsToken(token)?.subject

    private fun getClaimsToken(token: String): Claims? {
        return runCatching {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        }.getOrNull()
    }

    /**
     * Function responsible for fetching the duration
     *
     * @param duration is the duration in hour
     *
     * @return the duration in millisecond. If no value is sent, it will return the default value from properties
     */
    private fun fetchDuration(duration: Long?): Long =
        duration?.let { TimeUnit.HOURS.toMillis(it) } ?: TimeUnit.HOURS.toMillis(defaultDuration)
}

/**
 * Function responsible for validating JWT token
 *
 * @param date is the date itself
 *
 * @return true if expiration date is after the current time
 */
private fun Date.isValid(): Boolean {
    val now = Date(System.currentTimeMillis())
    return now.before(this)
}


